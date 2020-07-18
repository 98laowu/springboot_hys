package com.athys.springboothysum.service;
import com.athys.springboothysum.entity.UserRole;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author:admin
 * @Description:UserRole业务层接口
 * @Date 2019/6/14 0:16
 *****/
@Service
public interface UserRoleService {


    /***
     * UserRole多条件搜索方法
     * @param userRole
     * @return
     */
    List<UserRole> findList(UserRole userRole);

    /***
     * 删除UserRole
     * @param id
     */
    void delete(String id);

    /***
     * 修改UserRole数据
     * @param userRole
     */
    void update(UserRole userRole);

    /***
     * 新增UserRole
     * @param userRole
     */
    void add(UserRole userRole);

    /**
     * 根据ID查询UserRole
     * @param id
     * @return
     */
     UserRole findById(String id);

    /***
     * 查询所有UserRole
     * @return
     */
    List<UserRole> findAll();
}
