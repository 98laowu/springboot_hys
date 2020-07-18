package com.athys.springboothysum.service.impl;

import com.athys.springboothysum.dao.UserRoleMapper;
import com.athys.springboothysum.entity.UserRole;
import com.athys.springboothysum.service.UserRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:admin
 * @Description:UserRole业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;



    /**
     * UserRole条件查询
     * @param userRole
     * @return
     */
    @Override
    public List<UserRole> findList(UserRole userRole){
        //构建查询条件
        Example example = createExample(userRole);
        //根据构建的条件查询数据
        return userRoleMapper.selectByExample(example);
    }


    /**
     * UserRole构建查询对象
     * @param userRole
     * @return
     */
    public Example createExample(UserRole userRole){
        Example example=new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        if(userRole!=null){
            // 用户角色主键
            if(!StringUtils.isEmpty(userRole.getUserRoleId())){
                    criteria.andEqualTo("userRoleId",userRole.getUserRoleId());
            }
            // 角色主键
            if(!StringUtils.isEmpty(userRole.getRoleId())){
                    criteria.andEqualTo("roleId",userRole.getRoleId());
            }
            // 用户主键
            if(!StringUtils.isEmpty(userRole.getUserId())){
                    criteria.andEqualTo("userId",userRole.getUserId());
            }
            // 备注
            if(!StringUtils.isEmpty(userRole.getRemark())){
                    criteria.andEqualTo("remark",userRole.getRemark());
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
        userRoleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改UserRole
     * @param userRole
     */
    @Override
    public void update(UserRole userRole){
        userRoleMapper.updateByPrimaryKey(userRole);
    }

    /**
     * 增加UserRole
     * @param userRole
     */
    @Override
    public void add(UserRole userRole){
        userRoleMapper.insert(userRole);
    }

    /**
     * 根据ID查询UserRole
     * @param id
     * @return
     */
    @Override
    public UserRole findById(String id){
        return  userRoleMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询UserRole全部数据
     * @return
     */
    @Override
    public List<UserRole> findAll() {
        return userRoleMapper.selectAll();
    }
}
