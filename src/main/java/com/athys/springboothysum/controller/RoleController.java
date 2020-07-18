package com.athys.springboothysum.controller;

import com.athys.springboothysum.entity.Role;
import com.athys.springboothysum.entity.RolePermission;
import com.athys.springboothysum.entity.User;
import com.athys.springboothysum.entity.UserRole;
import com.athys.springboothysum.service.RolePermissionService;
import com.athys.springboothysum.service.RoleService;
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
@Api("角色管理模块")
@Slf4j
@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;

    /***
     * 多条件搜索Role数据
     * @param role
     * @return
     */
    @ApiOperation(value = "多条件搜索Role数据", httpMethod = "POST")
    @PostMapping(value = "/search" )
    public Result<List<Role>> findList(@RequestBody(required = false)  Role role){
//        Gson gson = new Gson();
//        Role role = gson.fromJson(roleJson, Role.class);
        //调用RoleService实现条件查询Role
        List<Role> list = roleService.findList(role);
        return new Result<List<Role>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除Role数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除Role数据", httpMethod = "DELETE")
    @DeleteMapping(value = "delete/{id}" )
    public Result delete(@PathVariable String id){
        //调用RoleService实现根据主键删除
        roleService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Role数据
     * @param id
     * @param pers
     * @return
     */
    @ApiOperation(value = "修改Role数据", httpMethod = "PUT")
    @Transactional(rollbackFor = Exception.class)
    @PutMapping(value="update/{id}")
    public Result update(@PathVariable String id,@RequestParam String[] pers){
        RolePermission rolePermission=new RolePermission();
        rolePermission.setRoleId(id);
        List<RolePermission> rolePermissionList=  rolePermissionService.findList(rolePermission);
        Object savePoint = null;
        try {
            //设置回滚点
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            for (RolePermission rp : rolePermissionList) {
                rolePermissionService.delete(rp.getRolePermissionId());
            }
            add(id, pers);
        }catch (Exception e){
            log.error(e.toString());
            //手工回滚异常
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
            return new Result(false,StatusCode.ERROR,"修改错误"+e.toString());
        }
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Role数据
     * @param id
     * @param pers
     * @return
     */
    @ApiOperation(value = "新增Role数据", httpMethod = "POST")
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/add/{id}")
    public Result add(@PathVariable String id,@RequestParam String[] pers){

//        Gson gson = new Gson();
//        Role role = gson.fromJson(roleJson, Role.class);
       Role role=roleService.findById(id);
        Object savePoint = null;
        try {
            //设置回滚点
            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
            if(role==null){
                roleService.add(role);
            }
            RolePermission rolePermission =new RolePermission();
            for(String p:pers){
               RolePermission rolePermission1= rolePermissionService.findById(id+"_"+p);
                if(rolePermission1!=null){
                    return new Result(false,StatusCode.ERROR,"已有"+rolePermission1.getPermissionId()+"该权限！");
                }
                rolePermission.setRolePermissionId(id+"_"+p);
                rolePermission.setRoleId(id);
                rolePermission.setPermissionId(p);
                rolePermissionService.add(rolePermission);
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
     * 根据ID查询Role数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询Role数据", httpMethod = "GET")
    @GetMapping("/{id}")
    public Result<Role> findById(@PathVariable String id){
        //调用RoleService实现根据主键查询Role
        Role role = roleService.findById(id);
        return new Result<Role>(true,StatusCode.OK,"查询成功",role);
    }

    /***
     * 查询Role全部数据
     * @return
     */
    @ApiOperation(value = "查询Role全部数据", httpMethod = "GET")
    @GetMapping
    public Result<List<Role>> findAll(){
        //调用RoleService实现查询所有Role
        List<Role> list = roleService.findAll();
        return new Result<List<Role>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
