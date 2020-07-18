package com.athys.springboothysum.service;
import com.athys.springboothysum.entity.Role;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author:admin
 * @Description:Role业务层接口
 * @Date 2019/6/14 0:16
 *****/
@Service
public interface RoleService {

    /***
     * Role多条件搜索方法
     * @param role
     * @return
     */
    List<Role> findList(Role role);

    /***
     * 删除Role
     * @param id
     */
    void delete(String id);

    /***
     * 修改Role数据
     * @param role
     */
    void update(Role role);

    /***
     * 新增Role
     * @param role
     */
    void add(Role role);

    /**
     * 根据ID查询Role
     * @param id
     * @return
     */
     Role findById(String id);

    /***
     * 查询所有Role
     * @return
     */
    List<Role> findAll();
}
