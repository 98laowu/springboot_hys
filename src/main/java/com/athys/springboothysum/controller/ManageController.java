package com.athys.springboothysum.controller;

import com.athys.springboothysum.entity.*;
import com.athys.springboothysum.service.PermissionService;
import com.athys.springboothysum.service.RolePermissionService;
import com.athys.springboothysum.service.UserRoleService;
import com.athys.springboothysum.service.UserService;
import com.athys.springboothysum.util.Result;
import com.athys.springboothysum.util.StatusCode;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/usermanage")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    /**
     * 加载主页
     * @param userJson
     * @return
     */
    @RequestMapping("/index")
    public Result<RolePermission> index(String userJson) {
        Gson gson = new Gson();
        User jsonToUser = gson.fromJson(userJson, User.class);
        if(jsonToUser!=null){
            //查找登录用户角色下的所有权限
            List<User> user= userService.findList(jsonToUser);
            UserRole userRole=new UserRole();
            userRole.setUserId(user.get(0).getUserId());
            List<UserRole> allUserRole= userRoleService.findList(userRole);
            List<RolePermission> allRolePermissionsList = new ArrayList<>();
            for (int i=0;i<allUserRole.size();i++) {
                RolePermission rolePermission=new RolePermission();
                rolePermission.setRoleId(allUserRole.get(i).getRoleId());
                List<RolePermission> rolePermissions=rolePermissionService.findList(rolePermission);
                allRolePermissionsList.addAll(rolePermissions);
            }
            if(allRolePermissionsList.size()<=0)
            {
                return new Result(false, StatusCode.ERROR, "您没有任何权限!");
            }
            //去重
            HashSet<RolePermission> set = new HashSet<RolePermission>(allRolePermissionsList);
            allRolePermissionsList.clear();
            allRolePermissionsList.addAll(set);
            log.info(Arrays.asList(allRolePermissionsList).toString());
            //在页面进行获取权限
            return new Result(true, StatusCode.OK, "已获取所有权限!",allRolePermissionsList);
        }
        return new Result(false, StatusCode.ERROR, "没有任何权限!");
    }
}
