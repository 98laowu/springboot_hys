package com.athys.springboothysum.controller;

import com.athys.springboothysum.entity.UserRole;
import com.athys.springboothysum.service.UserRoleService;
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
@RequestMapping("/userRole")
@CrossOrigin
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /***
     * UserRole分页条件搜索实现
     * @param userRole
     * @param page
     * @param size
     * @return
     */
    @PostMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@RequestBody(required = false)  UserRole userRole, @PathVariable int page, @PathVariable int size){
        //调用UserRoleService实现分页条件查询UserRole
        PageInfo<UserRole> pageInfo = userRoleService.findPage(userRole, page, size);
        return new Result(true, StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * UserRole分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size){
        //调用UserRoleService实现分页查询UserRole
        PageInfo<UserRole> pageInfo = userRoleService.findPage(page, size);
        return new Result<PageInfo>(true,StatusCode.OK,"查询成功",pageInfo);
    }

    /***
     * 多条件搜索UserRole数据
     * @param userRole
     * @return
     */
    @PostMapping(value = "/search" )
    public Result<List<UserRole>> findList(@RequestBody(required = false)  UserRole userRole){
        //调用UserRoleService实现条件查询UserRole
        List<UserRole> list = userRoleService.findList(userRole);
        return new Result<List<UserRole>>(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 根据ID删除UserRole数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}" )
    public Result delete(@PathVariable String id){
        //调用UserRoleService实现根据主键删除
        userRoleService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 修改UserRole数据
     * @param userRole
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody UserRole userRole, @PathVariable String id){
        //设置主键值
        userRole.setUserRoleId(id);
        //调用UserRoleService实现修改UserRole
        userRoleService.update(userRole);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /***
     * 新增UserRole数据
     * @param userRole
     * @return
     */
    @PostMapping
    public Result add(@RequestBody UserRole userRole){
        //调用UserRoleService实现添加UserRole
        userRoleService.add(userRole);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 根据ID查询UserRole数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
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
    @PostMapping(value = "/search" )
    public Result<List<UserRole>> findList(@RequestBody(required = false)  UserRole userRole){
        //调用UserRoleService实现条件查询UserRole
        List<UserRole> list = userRoleService.findList(userRole);
        return new Result<List<UserRole>>(true,StatusCode.OK,"查询成功",list);
    }


    /***
     * 查询UserRole全部数据
     * @return
     */
    @GetMapping
    public Result<List<UserRole>> findAll(){
        //调用UserRoleService实现查询所有UserRole
        List<UserRole> list = userRoleService.findAll();
        return new Result<List<UserRole>>(true, StatusCode.OK,"查询成功",list) ;
    }
}
