package com.athys.springboothysum.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
/****
 * @Author:admin
 * @Description:RolePermission构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="t_role_permission")
public class RolePermission implements Serializable{

	@Id
    @Column(name = "role_permission_id")
	private String rolePermissionId;//角色权限主键

    @Column(name = "role_id")
	private String roleId;//角色主键

    @Column(name = "permission_id")
	private String permissionId;//权限主键

    @Column(name = "remark")
	private String remark;//备注



	//get方法
	public String getRolePermissionId() {
		return rolePermissionId;
	}

	//set方法
	public void setRolePermissionId(String rolePermissionId) {
		this.rolePermissionId = rolePermissionId;
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
	public String getPermissionId() {
		return permissionId;
	}

	//set方法
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
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
		return "RolePermission{" +
				"rolePermissionId='" + rolePermissionId + '\'' +
				", roleId='" + roleId + '\'' +
				", permissionId='" + permissionId + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
