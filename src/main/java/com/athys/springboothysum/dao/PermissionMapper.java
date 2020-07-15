package com.athys.springboothysum.dao;
import com.athys.springboothysum.entity.Permission;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:Permission的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository
public interface PermissionMapper extends Mapper<Permission> {
}
