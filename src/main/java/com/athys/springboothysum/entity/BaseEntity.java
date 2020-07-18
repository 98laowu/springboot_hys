package com.athys.springboothysum.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 基础Bean
 */
@Data
public class BaseEntity implements Serializable {

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String remark;



}
