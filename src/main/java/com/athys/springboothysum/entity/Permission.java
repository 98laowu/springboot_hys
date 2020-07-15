package com.athys.springboothysum.entity;
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

    @Column(name = "remark")
	private String remark;//备注



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
				", remark='" + remark + '\'' +
				'}';
	}
}
