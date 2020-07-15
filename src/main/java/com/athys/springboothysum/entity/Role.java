package com.athys.springboothysum.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
/****
 * @Author:admin
 * @Description:Role构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="t_role")
public class Role implements Serializable{

	@Id
    @Column(name = "role_id")
	private String roleId;//角色主键

    @Column(name = "role_name")
	private String roleName;//角色名称

    @Column(name = "remark")
	private String remark;//备注



	//get方法
	public String getRoleId() {
		return roleId;
	}

	//set方法
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	//get方法
	public String getRoleName() {
		return roleName;
	}

	//set方法
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		return "Role{" +
				"roleId='" + roleId + '\'' +
				", roleName='" + roleName + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}
}
