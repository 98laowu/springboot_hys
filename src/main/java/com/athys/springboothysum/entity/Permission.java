package com.athys.springboothysum.entity;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.String;
/****
 * @Author:admin
 * @Description:Permission构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="t_permission")
public class Permission implements Serializable{

	@Id
    @Column(name = "permission_id")
	private String permissionId;//权限主键

    @Column(name = "permission_name")
	private String permissionName;//权限名称

	@Column(name = "parent_id")
	private String parentId;//父节点ID

	@Column(name = "category")
	private String category;//权限类型

    @Column(name = "remark")
	private String remark;//备注



	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
	public String getPermissionName() {
		return permissionName;
	}

	//set方法
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
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
		return "Permission{" +
				"permissionId='" + permissionId + '\'' +
				", permissionName='" + permissionName + '\'' +
				", parentId='" + parentId + '\'' +
				", category='" + category + '\'' +
				", remark='" + remark + '\'' +
				'}';
	}

	public void whichIsNotEmpty(Permission permission) {
		if (permission != null) {
			// 权限名称
			if (!StringUtils.isEmpty(permission.getPermissionName())) {
				this.setPermissionName(permission.getPermissionName());
			}
			// 父节点ID
			if (!StringUtils.isEmpty(permission.getParentId())) {
				this.setParentId(permission.getParentId());
			}
			// 权限类型
			if (!StringUtils.isEmpty(permission.getCategory())) {
				this.setCategory(permission.getCategory());
			}
			// 备注
			if (!StringUtils.isEmpty(permission.getRemark())) {
				this.setRemark(permission.getRemark());
			}
		}
	}
}
