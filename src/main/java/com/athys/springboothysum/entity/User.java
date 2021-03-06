package com.athys.springboothysum.entity;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.lang.String;
import java.lang.Integer;
/****
 * @Author:admin
 * @Description:User构建
 * @Date 2019/6/14 19:13
 *****/

@Table(name="t_user")
public class User implements Serializable{
	@Id
    @Column(name = "user_id")
	private String userId;//用户主键

    @Column(name = "person_name")
	private String personName;//姓名

    @Column(name = "user_name")
	private String userName;//用户名

    @Column(name = "psword")
	private String psword;//密码

    @Column(name = "gender")
	private String gender;//性别

    @Column(name = "JoinDate")
	private Date JoinDate;//注册时间

    @Column(name = "remark")
	private String remark;//备注

    @Column(name = "sts")
	private Integer sts;//角色状态(1为在线，0为下线)

    @Column(name = "birth")
	private Date birth;//出生日期

    @Column(name = "imagepath")
	private String imagepath;//头像路径

	@Column(name = "email")
	private String email;//邮箱

	@Column(name = "isonline")
	private Character isonline;//是否在线

	//get方法
	public String getUserId() {
		return userId;
	}

	//set方法
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//get方法
	public String getPersonName() {
		return personName;
	}

	//set方法
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	//get方法
	public String getUserName() {
		return userName;
	}

	//set方法
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//get方法
	public String getPsword() {
		return psword;
	}

	//set方法
	public void setPsword(String psword) {
		this.psword = psword;
	}
	//get方法
	public String getGender() {
		return gender;
	}

	//set方法
	public void setGender(String gender) {
		this.gender = gender;
	}
	//get方法
	public Date getJoinDate() {
		return JoinDate;
	}

	//set方法
	public void setJoinDate(Date JoinDate) {
		this.JoinDate = JoinDate;
	}
	//get方法
	public String getRemark() {
		return remark;
	}

	//set方法
	public void setRemark(String remark) {
		this.remark = remark;
	}
	//get方法
	public Integer getSts() {
		return sts;
	}

	//set方法
	public void setSts(Integer sts) {
		this.sts = sts;
	}
	//get方法
	public Date getBirth() {
		return birth;
	}

	//set方法
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	//get方法
	public String getImagepath() {
		return imagepath;
	}

	//set方法
	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	//get方法
	public String getEmail() {
		return email;
	}

	//set方法
	public void setEmail(String email) {
		this.email = email;
	}

	//get方法
	public Character getIsonline() {
		return isonline;
	}

	//set方法
	public void setIsonline(Character isonline) {
		this.isonline = isonline;
	}

	@Override
	public String toString() {
		return "User{" +
				"userId='" + userId + '\'' +
				", personName='" + personName + '\'' +
				", userName='" + userName + '\'' +
				", psword='" + psword + '\'' +
				", gender='" + gender + '\'' +
				", JoinDate=" + JoinDate +
				", remark='" + remark + '\'' +
				", sts=" + sts +
				", birth=" + birth +
				", imagepath='" + imagepath + '\'' +
				", email='" + email + '\'' +
				", isonline='" + isonline + '\'' +
				'}';
	}

	public void whichIsNotEmpty(User user) {
		if(user!=null){
			// 姓名
			if(!StringUtils.isEmpty(user.getPersonName())){
				this.setPersonName(user.getPersonName());
			}
			// 用户名
			if(!StringUtils.isEmpty(user.getUserName())){
				this.setUserName(user.getUserName());
			}
			// 密码
			if(!StringUtils.isEmpty(user.getPsword())){
				this.setPsword(user.getPsword());
			}
			// 性别
			if(!StringUtils.isEmpty(user.getGender())){
				this.setGender(user.getGender());
			}
			// 注册时间
			if(!StringUtils.isEmpty(user.getJoinDate())){
				this.setJoinDate(user.getJoinDate());
			}
			// 备注
			if(!StringUtils.isEmpty(user.getRemark())){
				this.setRemark(user.getRemark());
			}
			// 角色状态(1为正常，0为注销)
			if(!StringUtils.isEmpty(user.getSts())){
				this.setSts(user.getSts());
			}
			// 出生日期
			if(!StringUtils.isEmpty(user.getBirth())){
				this.setBirth(user.getBirth());
			}
			// 头像路径
			if(!StringUtils.isEmpty(user.getImagepath())){
				this.setImagepath(user.getImagepath());
			}
			// 邮箱
			if(!StringUtils.isEmpty(user.getEmail())){
				this.setEmail(user.getEmail());
			}
			// 是否在线
			if(!StringUtils.isEmpty(user.getIsonline())){
				this.setIsonline(user.getIsonline());
			}
		}
	}
}
