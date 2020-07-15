package com.athys.springboothysum.service.impl;

import com.athys.springboothysum.dao.RolePermissionMapper;
import com.athys.springboothysum.entity.RolePermission;
import com.athys.springboothysum.service.RolePermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/****
 * @Author:admin
 * @Description:RolePermission业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/

@Service
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;


    /**
     * RolePermission条件+分页查询
     * @param rolePermission 查询条件
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @Override
    public PageInfo<RolePermission> findPage(RolePermission rolePermission, int page, int size){
        //分页
        PageHelper.startPage(page,size);
        //搜索条件构建
        Example example = createExample(rolePermission);
        //执行搜索
        return new PageInfo<RolePermission>(rolePermissionMapper.selectByExample(example));
    }

    /**
     * RolePermission分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<RolePermission> findPage(int page, int size){
        //静态分页
        PageHelper.startPage(page,size);
        //分页查询
        return new PageInfo<RolePermission>(rolePermissionMapper.selectAll());
    }

    /**
     * RolePermission条件查询
     * @param rolePermission
     * @return
     */
    @Override
    public List<RolePermission> findList(RolePermission rolePermission){
        //构建查询条件
        Example example = createExample(rolePermission);
        //根据构建的条件查询数据
        return rolePermissionMapper.selectByExample(example);
    }


    /**
     * RolePermission构建查询对象
     * @param rolePermission
     * @return
     */
    public Example createExample(RolePermission rolePermission){
        Example example=new Example(RolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        if(rolePermission!=null){
            // 角色权限主键
            if(!StringUtils.isEmpty(rolePermission.getRolePermissionId())){
                    criteria.andEqualTo("rolePermissionId",rolePermission.getRolePermissionId());
            }
            // 角色主键
            if(!StringUtils.isEmpty(rolePermission.getRoleId())){
                    criteria.andEqualTo("roleId",rolePermission.getRoleId());
            }
            // 权限主键
            if(!StringUtils.isEmpty(rolePermission.getPermissionId())){
                    criteria.andEqualTo("permissionId",rolePermission.getPermissionId());
            }
            // 备注
            if(!StringUtils.isEmpty(rolePermission.getRemark())){
                    criteria.andEqualTo("remark",rolePermission.getRemark());
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
        rolePermissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改RolePermission
     * @param rolePermission
     */
    @Override
    public void update(RolePermission rolePermission){
        rolePermissionMapper.updateByPrimaryKey(rolePermission);
    }

    /**
     * 增加RolePermission
     * @param rolePermission
     */
    @Override
    public void add(RolePermission rolePermission){
        rolePermissionMapper.insert(rolePermission);
    }

    /**
     * 根据ID查询RolePermission
     * @param id
     * @return
     */
    @Override
    public RolePermission findById(String id){
        return  rolePermissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询RolePermission全部数据
     * @return
     */
    @Override
    public List<RolePermission> findAll() {
        return rolePermissionMapper.selectAll();
    }
}
