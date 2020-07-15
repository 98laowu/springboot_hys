package com.athys.springboothysum.service;
import com.athys.springboothysum.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/****
 * @Author:admin
 * @Description:User业务层接口
 * @Date 2019/6/14 0:16
 *****/
@Service
public interface UserService {

    /***
     * User多条件分页查询
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(User user, int page, int size);

    /***
     * User分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(int page, int size);

    /***
     * User多条件搜索方法
     * @param user
     * @return
     */
    List<User> findList(User user);

    /***
     * 删除User
     * @param id
     */
    void delete(String id);

    /***
     * 修改User数据
     * @param user
     */
    void update(User user);

    /***
     * 新增User
     * @param user
     */
    void add(User user);

    /**
     * 根据ID查询User
     * @param id
     * @return
     */
     User findById(String id);

    /**
     * 根据字段查询User
     * @param var1
     * @return
     */
    List<User> findByField(Object var1);

    /***
     * 查询所有User
     * @return
     */
    List<User> findAll();


}
