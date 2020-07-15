package com.athys.springboothysum.model;

import com.athys.springboothysum.entity.User;
import com.athys.springboothysum.service.UserService;
import com.athys.springboothysum.util.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
/**等价于private static final org.slf4j.log log = org.slf4j.logFactory.getlog(LogExample.class);*/
@Slf4j
@RestController
@RequestMapping("/usermanage")
@CrossOrigin
public class UserManageController {

    @Autowired
    private UserService userService;

    /***
     * 登陆
     * @param userName
     * @param passwd
     * @return
     */
    @GetMapping("/{userName}")
    public Result login(@RequestParam String userName, @RequestParam String passwd) {
        List<User> exsitUser = userService.findByField(userName);
        if (exsitUser == null) {
            return new Result(false, StatusCode.ERROR, "该用户未注册!");
        }
        if (!exsitUser.get(0).getPsword().equals(Md5Util.MD5(passwd) + userName)) {
            return new Result(false, StatusCode.ERROR, "密码错误,请重新输入!");
        }
        return new Result(true, StatusCode.OK, "登录成功!");
    }


    /***
     * 注册
     * @param json
     * @return
     */
    @GetMapping("/{json}")
    public Result register(@RequestParam String json) throws Exception {

        Gson gson = new Gson();
        //解析对象：第一个参数：待解析的字符串 第二个参数结果数据类型的Class对象
        User jsonToUser = gson.fromJson(json, User.class);
        if (StringUtils.isEmpty(jsonToUser.getUserName()) || StringUtils.isEmpty(jsonToUser.getPsword())) {
            return new Result(false, StatusCode.ERROR, "用户名和密码不能为空!");
        }
        // 验证用户名是否已经注册
        List<User> exsitUser = userService.findByField(jsonToUser.getUserName());
        if (exsitUser != null) {
            return new Result(false, StatusCode.ERROR, "该用户名已存在!");
        }
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]+$");
        if (!pattern.matcher(jsonToUser.getPsword()).find()) {
            return new Result(false, StatusCode.ERROR, "由字母和数字组成，但不能为纯数字， 不能为纯字母!");
        }
        // 验证邮箱是否已经注册
        List<User> exsitUser1 = userService.findByField(jsonToUser.getEmail());
        if (exsitUser1 != null) {
            return new Result(false, StatusCode.ERROR, "该邮箱已存在!");
        }
        String reg = "\\w+[@]{1}+[sina]{4}\\.com";//正则表达式，验证以新浪邮箱为例
        if (!jsonToUser.getEmail().matches(reg)) {
            return new Result(false, StatusCode.ERROR, "请输入邮箱格式!");
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String format = sdf.format(new Date());
        User user = new User();
        user.setBirth(jsonToUser.getBirth());
        user.setGender(jsonToUser.getGender());
        user.setPersonName(jsonToUser.getPersonName());
        user.setPsword(Md5Util.MD5(jsonToUser.getPsword()) + jsonToUser.getPersonName());
        user.setRemark(jsonToUser.getRemark());
        user.setSts(0);
        user.setEmail(jsonToUser.getEmail());
        user.setUserName(jsonToUser.getUserName());
        try {
            userService.add(user);
            return new Result(true, StatusCode.OK, "注册成功");
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "注册失败");
        }
    }

    /***
     * 设置头像
     * @param file
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result uploadPicture(@RequestParam(value = "file", required = false) MultipartFile file, @PathVariable String id) {
        Result result = new Result();
        Map<String, Object> map = new HashMap<String, Object>();
        File targetFile = null;
        String url = "";//返回存储路径
        int code = 1;
        System.out.println(file);
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        User currUser = userService.findById(id);
        if (fileName == null && fileName == "") {
//            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/upload/imgs/";//存储路径
//            String path = request.getSession().getServletContext().getRealPath("upload/imgs"); //文件存储位置
            // 图片存放的文件夹地址
            String filePath = "C:\\Users\\Lzy\\Desktop\\head\\";
            String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
            fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名
            //先判断文件是否存在
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String fileAdd = sdf.format(new Date());
            //获取文件夹路径
            //path+
            File file1 = new File("/" + fileAdd);
            //如果文件夹不存在则创建
            if (!file1.exists() && !file1.isDirectory()) {
                file1.mkdir();
            }
            //将图片存入文件夹
            targetFile = new File(file1, fileName);
            try {
                //将上传的文件写到服务器上指定的文件。
                file.transferTo(targetFile);
                //returnUrl+
                url = filePath + fileAdd + "/" + fileName;
                // 把图片路径写入数据库
                // 如果图片为空，给一个默认的图片
                currUser.setImagepath(url);
                userService.update(currUser);
                return new Result(true, StatusCode.OK, "图片上传成功");
            } catch (Exception e) {
                log.error(e.toString());
                e.printStackTrace();
                return new Result(false, StatusCode.ERROR, "系统异常，图片上传失败");
            }
        } else {
            return new Result(false, StatusCode.ERROR, "上传图片为空！");
        }
    }


    /***
     * 退出
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result changests(@PathVariable String id) throws Exception {
        User currUser = userService.findById(id);
        currUser.setSts(0);
        try {
            userService.update(currUser);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }

    /***
     * 修改密码
     * @param id
     * @param newPasswd
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result changePwd(@PathVariable String id, @RequestBody String newPasswd) throws Exception {
        User currUser = userService.findById(id);
        currUser.setPsword(newPasswd);
        try {
            userService.update(currUser);
            return new Result(true, StatusCode.OK, "修改成功");
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }


    /**
     * 验证并重新设置密码
     *
     * @param jsonPwd
     * @return
     */
    @PutMapping(value = "/{jsonPwd}")
    public Result resetPassword(String jsonPwd) throws Exception {
        if(isContentEmpty(jsonPwd).getCode()==20001){
            return isContentEmpty(jsonPwd);
        }
        User resultUser= isContentEmpty(jsonPwd).getData();
        String newPassword = JsonUtil.jsonToMap(jsonPwd).get("newPassword").toString();
        if (StringUtils.isEmpty(newPassword)) {
            return new Result(false, StatusCode.ERROR, "新密码不能为空！");
        }
        String requestHash = JsonUtil.jsonToMap(jsonPwd).get("hash").toString();
        String tamp = JsonUtil.jsonToMap(jsonPwd).get("tamp").toString();
        if (tamp.compareTo(tamp) > 0) {
            if (!resultUser.getRemark().equalsIgnoreCase(requestHash)) {
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
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUserName;

    /**
     * 验证用户
     *
     * @param jsonValidata
     * @return
     */
    @PutMapping(value = "/{jsonValidata}")
    public Result<Map<String, Object>> validata(String jsonValidata) throws Exception {
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
        String loginName = JsonUtil.jsonToMap(json).get("loginName").toString();
        if (StringUtils.isEmpty(loginName)) {
            return new Result(false, StatusCode.ERROR, "用户名不能为空！");
        }
        String email = JsonUtil.jsonToMap(json).get("email").toString();
        if (StringUtils.isEmpty(email)) {
            return new Result(false, StatusCode.ERROR, "邮箱不能为空！");
        }
        List<User> currUser = userService.findByField(loginName);
        if (currUser == null) {
            return new Result(false, StatusCode.ERROR, "用户不存在！",currUser.get(0));
        }
        return new Result(true, StatusCode.OK, "内容不为空", currUser.get(0));
    }

    /**
     * 获取重置密码需要的验证码
     *
     * @param user
     * @return
     */
    public Map<String, Object> sendMessage(User user) {
        Map<String, Object> resultMap = new HashMap<>();
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);//生成验证码
        //        Timestamp outDate = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);// 5分钟后过期
        String randomNum = (int) (Math.random() * 9 + 1) * 100000 + "";// 生成随机数
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        String currentTime = sf.format(c.getTime());// 生成5分钟后时间，用户校验是否过期
        String hash = Md5Util.MD5(randomNum);//生成MD5值
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
            user.setRemark(randomNum);
            userService.update(user);
            mailSender.send(mimeMessage);
            resultMap.put("hash", hash);
            resultMap.put("tamp", currentTime);
            resultMap.put("flag", true);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            resultMap.put("flag", false);
        }
        return resultMap;
    }
}