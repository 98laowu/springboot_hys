package com.athys.springboothysum.controller;

import com.alibaba.fastjson.JSON;
import com.athys.springboothysum.entity.User;
import com.athys.springboothysum.entity.UserRole;
import com.athys.springboothysum.service.UserRoleService;
import com.athys.springboothysum.service.UserService;
import com.athys.springboothysum.util.*;
import com.auth0.jwt.interfaces.Claim;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**等价于private static final org.slf4j.log log = org.slf4j.logFactory.getlog(LogExample.class);*/
@Api("登陆模块")
@Slf4j
@RestController
@RequestMapping("/userLog")
@CrossOrigin
public class LogController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    /***
     * 登陆
     * @param user
     * @return
     */
    @ApiOperation(value = "登录", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户信息", dataType = "User", paramType = "query")})
    @PostMapping("/login")
    public Result<String> login(@RequestBody User user) {
//        Gson gson = new Gson();
//        User user = gson.fromJson(userjson, User.class);
        User currUser=new User();
        currUser.setUserName(user.getUserName());
        List<User> exsitUser = userService.findList(currUser);
        if (exsitUser.size()==0) {
            return new Result(false, StatusCode.ERROR, "该用户未注册!");
        }
        if (exsitUser.get(0).getSts().equals(0)) {
            return new Result(false, StatusCode.ERROR, "该账户已被停用!");
        }
        if (!exsitUser.get(0).getPsword().equals(Md5Util.MD5(user.getPsword()))) {
            return new Result(false, StatusCode.ERROR, "密码错误,请重新输入!");
        }
        String token = JwtUtil.createToken(user);
        return new Result(true, StatusCode.OK, "请求成功!",token);
    }

//    /***
//     * 根据token解析用户信息
//     * @param
//     * @return
//     */
//    @ApiOperation(value = "根据token解析用户信息", httpMethod = "GET")
//    @GetMapping("/parsejwt")
//    public Result<Map<String, Claim>> parsejwt(@RequestParam String token) {
//        if(token.equals("")){
//            return new Result(false, StatusCode.ERROR, "token不能为空!",token);
//        }
//        Map<String, Claim> result= JwtUtil.verifyToken(token);
//        return new Result(true, StatusCode.OK, "请求成功!",result);
//    }

    /***
     * 注册
     * @param jsonToUser
     * @return
     */
    @ApiOperation(value = "注册", httpMethod = "POST")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/register")
    public Result register(@RequestBody User jsonToUser) throws Exception {

//        Gson gson = new Gson();
//        User jsonToUser = gson.fromJson(userJson, User.class);
        if (StringUtils.isEmpty(jsonToUser.getUserName()) || StringUtils.isEmpty(jsonToUser.getPsword())) {
            return new Result(false, StatusCode.ERROR, "用户名和密码不能为空!");
        }
        User user0=new User();
        user0.setUserName(jsonToUser.getUserName());
        // 验证用户名是否已经注册
        List<User> exsitUser = userService.findList(user0);
        if (exsitUser.size()>0) {
            return new Result(false, StatusCode.ERROR, "该用户名已存在!");
        }
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$");
        if (!pattern.matcher(jsonToUser.getPsword()).find()) {
            return new Result(false, StatusCode.ERROR, "密码由字母和数字组成，但不能为纯数字， 不能为纯字母!");
        }
        User user1=new User();
        user1.setEmail(jsonToUser.getEmail());
        List<User> exsitUser1 = userService.findList(user1);
        // 验证邮箱是否已经注册
        if(exsitUser1.size()>0) {
            return new Result(false, StatusCode.ERROR, "该邮箱已存在!");
        }
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(RULE_EMAIL);
        if (!p.matcher(jsonToUser.getEmail()).matches()) {
            return new Result(false, StatusCode.ERROR, "请输入正确的邮箱格式!");
        }
        User user = new User();
        String guid=GuidUtil.getUUID();
        user.setUserId(guid);
        user.setBirth(jsonToUser.getBirth());
        user.setGender(jsonToUser.getGender());
        user.setPersonName(jsonToUser.getPersonName());
        user.setPsword(Md5Util.MD5(jsonToUser.getPsword()));
        user.setRemark(jsonToUser.getRemark());
        Date newTime = new Date();
        //SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//等价于now.toLocaleString()
        user.setJoinDate(newTime);
        user.setSts(1);
        user.setEmail(jsonToUser.getEmail());
        user.setUserName(jsonToUser.getUserName());
        UserRole userRole=new UserRole();
        userRole.setRoleId("r1");
        userRole.setUserId(guid);
        userRole.setUserRoleId(GuidUtil.getUUID());
        Object savePoint = null;
        try {
            //设置回滚点
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            userService.add(user);
            userRoleService.add(userRole);
            return new Result(true, StatusCode.OK, "注册成功");
        } catch (Exception e) {
            log.error(e.toString());
            //手工回滚异常
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return new Result(false, StatusCode.ERROR, "注册错误"+e.toString());
        }
    }
    /** 头像保存路径 */
    public static final String WINDOWS_PROFILES_PATH = "C:/hysum/profiles/";
    public static final String LINUX_PROFILES_PATH = "/root/hysum/profiles/";
    /***
     * 设置头像
     * @param file
     * @param id
     * @return
     */
    @ApiOperation(value = "设置头像", httpMethod = "PUT")
    @PutMapping(value = "/uploadPicture/{id}")
    public Result uploadPicture(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable String id) {
        // 根据Windows和Linux配置不同的头像保存路径
        String OSName = System.getProperty("os.name");
        String profilesPath = OSName.toLowerCase().startsWith("win") ?WINDOWS_PROFILES_PATH:LINUX_PROFILES_PATH;

        if (!file.isEmpty()) {
            // 当前用户
            User currentUser = userService.findById(id);
            String profilePathAndNameDB =currentUser.getImagepath();
            // 默认以原来的头像名称为新头像的名称，这样可以直接替换掉文件夹中对应的旧头像
            String newProfileName = profilePathAndNameDB;
            // 若头像名称不存在
            if (profilePathAndNameDB == null || "".equals(profilePathAndNameDB)) {
                newProfileName = profilesPath+ System.currentTimeMillis()+ file.getOriginalFilename();
                // 路径存库
                currentUser.setImagepath(newProfileName);
                userService.update(currentUser);
            }
            // 磁盘保存
            BufferedOutputStream out = null;
            try {
                File folder = new File(profilesPath);
                if (!folder.exists())
                    folder.mkdirs();
                out = new BufferedOutputStream(new FileOutputStream(newProfileName));
                // 写入新文件
                out.write(file.getBytes());
                out.flush();
            } catch (Exception e) {
                log.error(e.toString());
                return new Result(false, StatusCode.ERROR, "设置头像错误"+e.toString());
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error(e.toString());
                    return new Result(false, StatusCode.ERROR, "设置头像错误"+e.toString());
                }
            }
            return new Result(false, StatusCode.ERROR, "设置头像成功");
        } else {
            return new Result(false, StatusCode.ERROR, "设置头像失败");
        }
    }



    /***
     * 修改基础信息(包括退出改变状态)
     * @param id
     * @param newUser
     * @retur
     */
    @ApiOperation(value = "修改基础信息(包括退出改变状态)", httpMethod = "POST")
    //资源使用数据库自增主键作为标识信息，而创建的资源的标识信息到底是什么只能由服务端提供，这个时候就必须使用POST。
    @PostMapping(value = "/changeInfo/{id}")
    public Result changeInfo(@PathVariable String id,@RequestBody User newUser) throws Exception {
        User curr= userService.findById(id);
        curr.whichIsNotEmpty(newUser);
        try {
            userService.update(curr);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            log.error(e.toString());
            
            return new Result(false, StatusCode.ERROR, "修改错误"+e.toString());
        }
    }


    /**
     * 验证并重新设置密码
     *
     * @param jsonPwd
     * @return
     */
    @ApiOperation(value = "验证并重新设置密码", httpMethod = "PUT")
    @PutMapping(value = "/resetPassword")
    public Result resetPassword(@RequestBody String jsonPwd) throws Exception {
        Map<String,Object> map=  JsonUtil.jsonToMap(jsonPwd);
        Result<User> result=isContentEmpty(jsonPwd);
        if(result.getCode()==20001){
            return result;
        }
        User resultUser= result.getData();
        String newPassword = map.get("newPassword").toString();
        if (StringUtils.isEmpty(newPassword)) {
            return new Result(false, StatusCode.ERROR, "新密码不能为空！");
        }
        String requestHash = map.get("hash").toString();
        String tamp = map.get("tamp").toString();
        if (Math.abs(tamp.compareTo(String.valueOf(System.currentTimeMillis()))) >= 0) {
            if (! Md5Util.MD5(resultUser.getRemark()).equalsIgnoreCase(requestHash)) {
                //验证码不正确，校验失败
                return new Result(false, StatusCode.ERROR, "验证码不正确，校验失败！");
            }
        } else {
            // 超时
            return new Result(false, StatusCode.ERROR, "超时！");
        }
        try {
            //修改密码
            resultUser.setPsword(newPassword);
            userService.update(resultUser);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            log.error(e.toString());
            
            return new Result(false, StatusCode.ERROR, "修改错误"+e.toString());
        }
    }

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUserName;

    /**
     * 邮箱验证
     *
     * @param jsonValidata
     * @return
     */
    @ApiOperation(value = "邮箱验证", httpMethod = "PUT")
    @PutMapping(value = "/validata")
    public Result<Map<String, Object>> validata(@RequestBody String jsonValidata) throws Exception {
        User resultUser= isContentEmpty(jsonValidata).getData();
        Map<String, Object> resultMap = sendMessage(resultUser);
        if(resultMap.get("flag").equals(false)){
            return new Result(false, StatusCode.ERROR, "发送邮件失败！");
        }
        return new Result(true, StatusCode.OK, "验证发送成功", resultMap);
    }

    /**
     * 判断传入的用户名和邮箱是否为空
     *
     * @param json
     * @return
     */
    public Result<User> isContentEmpty(String json) throws Exception {
        String userId = JsonUtil.jsonToMap(json).get("userId").toString();
        if (StringUtils.isEmpty(userId)) {
            return new Result(false, StatusCode.ERROR, "ID不能为空！");
        }
        String loginName = JsonUtil.jsonToMap(json).get("loginName").toString();
        if (StringUtils.isEmpty(loginName)) {
            return new Result(false, StatusCode.ERROR, "用户名不能为空！");
        }
        String email = JsonUtil.jsonToMap(json).get("email").toString();
        if (StringUtils.isEmpty(email)) {
            return new Result(false, StatusCode.ERROR, "邮箱不能为空！");
        }
        User currUser = userService.findById(userId);
        if (currUser == null) {
            return new Result(false, StatusCode.ERROR, "用户不存在！",currUser);
        }
        return new Result(true, StatusCode.OK, "内容不为空", currUser);
    }

    /**
     * 获取重置密码需要的验证码
     *
     * @param user
     * @return
     */
    public Map<String, Object> sendMessage(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        //String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成验证码
        // Timestamp outDate = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);// 5分钟后过期
        Integer randomNum = (int) ((Math.random() * 9 + 1) * 100000);// 生成随机数
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        String hash = Md5Util.MD5(randomNum.toString());//生成MD5值
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("您好<br/>");
        stringBuilder.append("您的验证码是：").append(randomNum).append("<br/>");
        stringBuilder.append("您可以复制此验证码并返回，以验证您的邮箱。<br/>");
        stringBuilder.append("此验证码只能使用一次，在5分钟内有效。验证成功则自动失效。<br/>");
        stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            //发送验证码到邮箱
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            //这里只是设置username 并没有设置host和password，因为host和password在springboot启动创建JavaMailSender实例的时候已经读取了
            mimeMessageHelper.setFrom(mailUserName);
            mimeMessageHelper.setTo(user.getEmail());
            mimeMessage.setSubject("邮箱验证_hys");
            mimeMessageHelper.setText(stringBuilder.toString(), true);
            //验证码存入remark字段
            user.setRemark(randomNum.toString());
            userService.update(user);
            mailSender.send(mimeMessage);
            resultMap.put("hash", hash);
            resultMap.put("tamp", currentTime);
            resultMap.put("flag", true);
        } catch (Exception e) {
            log.error(e.toString());
            
            resultMap.put("flag", false);
        }
        return resultMap;
    }
}