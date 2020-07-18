package com.athys.springboothysum.service;
import com.athys.springboothysum.entity.RolePermission;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author:admin
 * @Description:RolePermission业务层接口
 * @Date 2019/6/14 0:16
 *****/
@Service
public interface RolePermissionService {

    /***
     * RolePermission多条件搜索方法
     * @param rolePermission
     * @return
     */
    List<RolePermission> findList(RolePermission rolePermission);

    /***
     * 删除RolePermission
     * @param id
     */
    void delete(String id);

    /***
     * 修改RolePermission数据
     * @param rolePermission
     */
    void update(RolePermission rolePermission);

    /***
     * 新增RolePermission
     * @param rolePermission
     */
    void add(RolePermission rolePermission);

    /**
     * 根据ID查询RolePermission
     * @param id
     * @return
     */
     RolePermission findById(String id);

    /***
     * 查询所有RolePermission
     * @return
     */
    List<RolePermission> findAll();
}
