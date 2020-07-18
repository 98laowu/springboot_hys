package com.athys.springboothysum.service.impl;

import com.athys.springboothysum.dao.PermissionMapper;
import com.athys.springboothysum.entity.Permission;
import com.athys.springboothysum.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Column;
import java.util.List;

/****
 * @Author:admin
 * @Description:Permission业务层接口实现类
 * @Date 2019/6/14 0:16
 *****/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;




    /**
     * Permission条件查询
     * @param permission
     * @return
     */
    @Override
    public List<Permission> findList(Permission permission){
        //构建查询条件
        Example example = createExample(permission);
        //根据构建的条件查询数据
        return permissionMapper.selectByExample(example);
    }


    /**
     * Permission构建查询对象
     * @param permission
     * @return
     */
    public Example createExample(Permission permission){
        Example example=new Example(Permission.class);
        Example.Criteria criteria = example.createCriteria();
        if(permission!=null){
            // 权限主键
            if(!StringUtils.isEmpty(permission.getPermissionId())){
                    criteria.andEqualTo("permissionId",permission.getPermissionId());
            }
            // 权限名称
            if(!StringUtils.isEmpty(permission.getPermissionName())){
                    criteria.andEqualTo("permissionName",permission.getPermissionName());
            }
            // 父节点ID
            if(!StringUtils.isEmpty(permission.getParentId())){
                criteria.andEqualTo("parentId",permission.getParentId());
            }
            // 权限类型
            if(!StringUtils.isEmpty(permission.getCategory())){
                criteria.andEqualTo("category",permission.getCategory());
            }
            // 备注
            if(!StringUtils.isEmpty(permission.getRemark())){
                    criteria.andEqualTo("remark",permission.getRemark());
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
        permissionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改Permission
     * @param permission
     */
    @Override
    public void update(Permission permission){
        permissionMapper.updateByPrimaryKey(permission);
    }

    /**
     * 增加Permission
     * @param permission
     */
    @Override
    public void add(Permission permission){
        permissionMapper.insert(permission);
    }

    /**
     * 根据ID查询Permission
     * @param id
     * @return
     */
    @Override
    public Permission findById(String id){
        return  permissionMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询Permission全部数据
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return permissionMapper.selectAll();
    }
}
