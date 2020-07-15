package com.athys.springboothysum.controller;

import com.athys.springboothysum.entity.Permission;
import com.athys.springboothysum.service.PermissionService;
import com.athys.springboothysum.util.Result;
import com.athys.springboothysum.util.StatusCode;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/

@RestController
@RequestMapping("/permission")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /***
     * Permission分页条件搜索实现
     * @param permission
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  Permission permission, @PathVariable int page, @PathVariable int size){
        //调用PermissionService实现分页条件查询Permission
        PageInfo<Permission> pageInfo = permissionService.findPage(permission, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * Permission分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size){
        //调用PermissionService实现分页查询Permission
        PageInfo<Permission> pageInfo = permissionService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索Permission数据
     * @param permission
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<Permission>> findList(@RequestBody(required = false)  Permission permission){
        //调用PermissionService实现条件查询Permission
        List<Permission> list = permissionService.findList(permission);
        return new Result<List<Permission>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除Permission数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用PermissionService实现根据主键删除
        permissionService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改Permission数据
     * @param permission
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Permission permission, @PathVariable String id){
        //设置主键值
        permission.setPermissionId(id);
        //调用PermissionService实现修改Permission
        permissionService.update(permission);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Permission数据
     * @param permission
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Permission permission){
        //调用PermissionService实现添加Permission
        permissionService.add(permission);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Permission数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Permission> findById(@PathVariable String id){
        //调用PermissionService实现根据主键查询Permission
        Permission permission = permissionService.findById(id);
        return new Result<Permission>(true,StatusCode.OK,"查询成功",permission);
    }

    /***
     * 查询Permission全部数据
     * @return
     */
    @GetMapping
    public Result<List<Permission>> findAll(){
        //调用PermissionService实现查询所有Permission
        List<Permission> list = permissionService.findAll();
        return new Result<List<Permission>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
