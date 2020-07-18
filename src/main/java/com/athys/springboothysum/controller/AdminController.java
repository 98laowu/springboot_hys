package com.athys.springboothysum.controller;

import com.athys.springboothysum.entity.Role;
import com.athys.springboothysum.entity.User;
import com.athys.springboothysum.entity.UserRole;
import com.athys.springboothysum.service.RoleService;
import com.athys.springboothysum.service.UserRoleService;
import com.athys.springboothysum.service.UserService;
import com.athys.springboothysum.util.GuidUtil;
import com.athys.springboothysum.util.Result;
import com.athys.springboothysum.util.StatusCode;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@Api("管理员模块")
@Slf4j
@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    /***
     * 根据ID删除UserRole数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除UserRole数据", httpMethod = "DELETE")
    @DeleteMapping(value = "delete/{id}" )
    public Result delete(@PathVariable String id){
        try {
            //调用UserRoleService实现根据主键删除
            userRoleService.delete(id);
            return new Result(true,StatusCode.OK,"删除成功");
        } catch (Exception e) {
            log.error(e.toString());
            return new Result(false, StatusCode.ERROR, "删除失败错误"+e.toString());
        }
    }

    /***
     * 修改UserRole数据
     * @param id
     * @param roleIds
     * @return
     */
    @ApiOperation(value = "修改UserRole数据", httpMethod = "PUT")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping(value="update/{id}")
    public Result update(@PathVariable  String id,@RequestParam String[] roleIds){
        UserRole userRole=new UserRole();
        userRole.setUserId(id);
        List<UserRole> userRoleList=  userRoleService.findList(userRole);
        Object savePoint = null;
        try {
            //设置回滚点
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            for (UserRole ur : userRoleList) {
                userRoleService.delete(ur.getUserRoleId());
            }
            add(id, roleIds);
        }catch(Exception e){
            log.error(e.toString());
            //手工回滚异常
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return new Result(false,StatusCode.ERROR,"添加错误"+e.toString());
        }
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增UserRole数据组
     * @param id
     * @param roleIds
     * @return
     */
    @ApiOperation(value = "新增UserRole数据组", httpMethod = "POST")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value="add/{id}")
    public Result add(@PathVariable  String id,@RequestParam  String[] roleIds){
        Object savePoint = null;
        User user= userService.findById(id);
        if(user==null){
            return new Result(false,StatusCode.ERROR,"没有该用户");
        }
        try {
            //设置回滚点
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            for (String r : roleIds) {
                Role role=roleService.findById(r);
                if(role==null){
                    return new Result(false,StatusCode.ERROR,"没有"+r+"角色");
                }
            }
            for (String r : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setUserRoleId("u"+id+r);
                userRole.setUserId(id);
                userRole.setRoleId(r);
                userRoleService.add(userRole);
            }
        }catch (Exception e){
            log.error(e.toString());
            //手工回滚异常
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return new Result(false,StatusCode.ERROR,"添加错误"+e.toString());
        }
        return new Result(true,StatusCode.OK,"添加成功");
    }


    /***
     * 根据用户ID,重置该用户密码,启用或停用该账户
     * @param id
     * @param newPwd
     * @return
     */
    @ApiOperation(value = "根据用户ID,重置该用户密码,启用或停用该账户", httpMethod = "PUT")
    @PutMapping("resetUserPwd/{id}")
    public Result resetUserPwd(@PathVariable String id,@PathVariable String newPwd){
        try {
            User user = userService.findById(id);
            user.setPsword(newPwd);
            userService.update(user);
            return new Result(true,StatusCode.OK,"重置密码成功");
        } catch (Exception e) {
            log.error(e.toString());
            
            return new Result(false, StatusCode.ERROR, "重置密码错误"+e.toString());
        }
    }


  
    /***
     * 根据ID查询UserRole数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询UserRole数据", httpMethod = "GET")
    @GetMapping("findById/{id}")
    public Result<UserRole> findById(@PathVariable String id){
        //调用UserRoleService实现根据主键查询UserRole
        UserRole userRole = userRoleService.findById(id);
        return new Result<UserRole>(true,StatusCode.OK,"查询成功",userRole);
    }




    /***
     * 多条件搜索UserRole数据
     * @param userRole
     * @return
     */
    @ApiOperation(value = "多条件搜索UserRole数据", httpMethod = "POST")
    @PostMapping(value = "/search" )
    public Result<List<UserRole>> findList(@RequestBody(required = false)  UserRole userRole){
//        Gson gson = new Gson();
//        UserRole userRole = gson.fromJson(userRoleJson, UserRole.class);
        //调用UserRoleService实现条件查询UserRole
        List<UserRole> list = userRoleService.findList(userRole);
        return new Result<List<UserRole>>(true,StatusCode.OK,"查询成功",list);
    }


    /***
     * 查询UserRole全部数据
     * @return
     */
    @ApiOperation(value = "查询UserRole全部数据", httpMethod = "GET")
    @GetMapping
    public Result<List<UserRole>> findAll(){
        //调用UserRoleService实现查询所有UserRole
        List<UserRole> list = userRoleService.findAll();
        return new Result<List<UserRole>>(true, StatusCode.OK,"查询成功",list) ;
    }

}
