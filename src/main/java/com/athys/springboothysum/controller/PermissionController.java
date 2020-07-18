package com.athys.springboothysum.controller;

import com.athys.springboothysum.entity.Permission;
import com.athys.springboothysum.entity.User;
import com.athys.springboothysum.service.PermissionService;
import com.athys.springboothysum.util.Result;
import com.athys.springboothysum.util.StatusCode;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:admin
 * @Description:
 * @Date 2019/6/14 0:18
 *****/
@Api("权限模块")
@Slf4j
@RestController
@RequestMapping("/permission")
@CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /***
     * 多条件搜索Permission数据
     * @param permission
     * @return
     */
    @ApiOperation(value = "多条件搜索Permission数据", httpMethod = "POST")
    @PostMapping(value = "/search" )
    public Result<List<Permission>> findList(@RequestBody(required = false)  Permission permission){
        List<Permission> list = permissionService.findList(permission);
        return new Result<List<Permission>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除Permission数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除Permission数据", httpMethod = "DELETE")
    @DeleteMapping(value = "delete/{id}" )
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
    @ApiOperation(value = "修改Permission数据", httpMethod = "POST")
    @PostMapping(value="update/{id}")
    public Result update(@RequestBody Permission permission, @PathVariable String id){
        Permission curr= permissionService.findById(id);
        curr.whichIsNotEmpty(permission);
        permissionService.update(curr);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增Permission数据
     * @param permission
     * @return
     */
    @ApiOperation(value = "新增Permission数据", httpMethod = "POST")
    @PostMapping("/add")
    public Result add(@RequestBody Permission permission){
        permissionService.add(permission);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询Permission数据
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID查询Permission数据", httpMethod = "GET")
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
    @ApiOperation(value = "查询Permission全部数据", httpMethod = "GET")
    @GetMapping
    public Result<List<Permission>> findAll(){
        //调用PermissionService实现查询所有Permission
        List<Permission> list = permissionService.findAll();
        return new Result<List<Permission>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
