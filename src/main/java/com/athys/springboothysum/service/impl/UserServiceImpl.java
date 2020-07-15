package com.athys.springboothysum.service.impl;

import com.athys.springboothysum.dao.UserMapper;
import com.athys.springboothysum.entity.User;
import com.athys.springboothysum.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:admin
 * @Description:User业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    /**
     * User条件+分页查询
     * @param user 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<User> findPage(User user, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(user);
        //执行搜索
        return new PageInfo<User>(userMapper.selectByExample(example));
    }

    /**
     * User分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<User> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<User>(userMapper.selectAll());
    }

    /**
     * User条件查询
     * @param user
     * @return
     */
    @Override
    public List<User> findList(User user){
        //构建查询条件
        Example example = createExample(user);
        //根据构建的条件查询数据
        return userMapper.selectByExample(example);
    }


    /**
     * User构建查询对象
     * @param user
     * @return
     */
    public Example createExample(User user){
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if(user!=null){
            // 用户主键
            if(!StringUtils.isEmpty(user.getUserId())){
                    criteria.andEqualTo("userId",user.getUserId());
            }
            // 姓名
            if(!StringUtils.isEmpty(user.getPersonName())){
                    criteria.andEqualTo("personName",user.getPersonName());
            }
            // 用户名
            if(!StringUtils.isEmpty(user.getUserName())){
                    criteria.andEqualTo("userName",user.getUserName());
            }
            // 密码
            if(!StringUtils.isEmpty(user.getPsword())){
                    criteria.andEqualTo("psword",user.getPsword());
            }
            // 性别
            if(!StringUtils.isEmpty(user.getGender())){
                    criteria.andEqualTo("gender",user.getGender());
            }
            // 注册时间
            if(!StringUtils.isEmpty(user.getJoinDate())){
                    criteria.andEqualTo("JoinDate",user.getJoinDate());
            }
            // 备注
            if(!StringUtils.isEmpty(user.getRemark())){
                    criteria.andEqualTo("remark",user.getRemark());
            }
            // 角色状态(1为正常，0为注销)
            if(!StringUtils.isEmpty(user.getSts())){
                    criteria.andEqualTo("sts",user.getSts());
            }
            // 出生日期
            if(!StringUtils.isEmpty(user.getBirth())){
                    criteria.andEqualTo("birth",user.getBirth());
            }
            // 头像路径
            if(!StringUtils.isEmpty(user.getImagepath())){
                    criteria.andEqualTo("imagepath",user.getImagepath());
            }
        }
        return example;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public void delete(String id){
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改User
     * @param user
     */
    @Override
    public void update(User user){
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 增加User
     * @param user
     */
    @Override
    public void add(User user){
        userMapper.insert(user);
    }

    /**
     * 根据ID查询User
     * @param id
     * @return
     */
    @Override
    public User findById(String id){
        return  userMapper.selectByPrimaryKey(id);
    }


    /**
     * 根据字段查询User
     * @param var1
     * @return
     */
    @Override
    public List<User> findByField(Object var1){return  userMapper.selectByExample(var1);}


    /**
     * 查询User全部数据
     * @return
     */
    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }
}
