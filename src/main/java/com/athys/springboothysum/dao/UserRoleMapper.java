package com.athys.springboothysum.dao;
import com.athys.springboothysum.entity.UserRole;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:UserRole的Dao
 * @Date 2019/6/14 0:12
 *****/
@Repository
public interface UserRoleMapper extends Mapper<UserRole> {
}
