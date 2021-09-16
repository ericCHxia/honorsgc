/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: UserAttend.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/11 下午5:02
 */

package com.hdu.honor.user;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_attend_rate")
@ToString
public class UserAttend implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @ExcelIgnore
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ExcelProperty("学号")
    @Column(name = "num", unique = true, length = 10)
    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @ExcelProperty("姓名")
    @Column(name = "name", length = 100)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelIgnore
    @Column(name = "priv")
    private Integer privilege;

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    @ExcelProperty("学院")
    @Column(name = "sch", length = 50)
    private String college;

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    @ExcelProperty("专业")
    @Column(name = "subj", length = 50)
    private String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @ExcelProperty("班级")
    @Column(name = "clas", length = 10)
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @ExcelProperty("共同体加入数")
    @Column(name = "gtts")
    private Long communitiesCount;

    public Long getCommunitiesCount() {
        return communitiesCount;
    }

    public void setCommunitiesCount(Long communitiesCount) {
        this.communitiesCount = communitiesCount;
    }

    @ExcelProperty("文章发表数")
    @Column(name = "articles")
    private Long articles;

    public Long getArticles() {
        return articles;
    }

    public void setArticles(Long articles) {
        this.articles = articles;
    }

    @ExcelProperty("共同体活动总数")
    @Column(name = "total")
    private Long totalAttend;

    public Long getTotalAttend() {
        return totalAttend;
    }

    public void setTotalAttend(Long totalAttend) {
        this.totalAttend = totalAttend;
    }

    @ExcelProperty("活动参与数")
    @Column(name = "attends")
    private Long attends;

    public Long getAttends() {
        return attends;
    }

    public void setAttends(Long attends) {
        this.attends = attends;
    }

    @ExcelProperty("出勤率")
    @Column(name = "rate")
    private Double attendRate;

    public Double getAttendRate() {
        return attendRate;
    }

    public void setAttendRate(Double attendRate) {
        this.attendRate = attendRate;
    }
}
