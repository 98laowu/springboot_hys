package com.athys.springboothysum.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
/****
 * @Author:admin
 * @Description:UserRole构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="t_user_role")
public class UserRole implements Serializable{

	@Id
    @Column(name = "user_role_id")
	private String userRoleId;//用户角色主键

    @Column(name = "role_id")
	private String roleId;//角色主键

    @Column(name = "user_id")
	private String userId;//用户主键

    @Column(name = "remark")
	private String remark;//备注



	//get方法
	public String getUserRoleId() {
		return userRoleId;
	}

	//set方法
	public void setUserRoleId(String userRoleId) {
		this.userRoleId = userRoleId;
	}
	//get方法
	public String getRoleId() {
		return roleId;
	}

	//set方法
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	//get方法
	public String getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//get方法
	public String getRemark() {
		return remark;
	}

	//set方法
	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "UserRole{" +
				"userRoleId='" + userRoleId + '\'' +
				", roleId='" + roleId + '\'' +
				", userId='" + userId + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
