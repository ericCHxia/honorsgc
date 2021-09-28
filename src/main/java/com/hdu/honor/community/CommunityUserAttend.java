/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityUserAttend.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/27 下午11:30
 */

package com.hdu.honor.community;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hdu.honor.community.participant.ParticipantTypeConverter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "gtt_usr_attend_info")
@ToString
public class CommunityUserAttend {
    @EmbeddedId
    @ExcelIgnore
    private CommunityUser communityUser;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "gttid",insertable=false,updatable=false)
    @ExcelIgnore
    private Community community;

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    @Column(name = "typ")
    @ExcelProperty(value = "参与方式",converter = ParticipantTypeConverter.class,index = 2)
    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name = "name", length = 100)
    @ExcelProperty(value = "姓名",index = 1)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "num", length = 10)
    @ExcelProperty(value = "学号",index = 0)
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Column(name = "total")
    @ExcelProperty(value = "活动参与次数",index = 3)
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
