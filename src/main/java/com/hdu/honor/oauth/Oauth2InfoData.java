/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Oauth2InfoData.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/25 下午9:08
 */

package com.hdu.honor.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hdu.honor.user.User;

public class Oauth2InfoData {
    @JsonProperty("CLASSID")
    private String classId;
    @JsonProperty("DORMBUILDING")
    private String dormBuilding;
    @JsonProperty("DORMROOM")
    private String dormRoom;
    @JsonProperty("MAJORCODE")
    private String majorCode;
    @JsonProperty("MAJORNAME")
    private String majorName;
    @JsonProperty("RUXUESJ")
    private String enrollmentDate;
    @JsonProperty("STAFFID")
    private String staffId;
    @JsonProperty("STAFFNAME")
    private String staffName;
    @JsonProperty("TEACHERID")
    private String teacherId;
    @JsonProperty("TEACHERNAME")
    private String teacherName;
    @JsonProperty("UNITCODE")
    private String unitCode;
    @JsonProperty("UNITNAME")
    private String unitName;
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getClassId() {
        return classId;
    }

    public void setDormBuilding(String dormBuilding) {
        this.dormBuilding = dormBuilding;
    }
    public String getDormBuilding() {
        return dormBuilding;
    }

    public void setDormRoom(String dormRoom) {
        this.dormRoom = dormRoom;
    }
    public String getDormRoom() {
        return dormRoom;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }
    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
    public String getMajorName() {
        return majorName;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
    public String getStaffId() {
        return staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
    public String getStaffName() {
        return staffName;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
    public String getTeacherName() {
        return teacherName;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    public String getUnitName() {
        return unitName;
    }

    public User toUser(){
        User user = new User();
        user.setPrivilege(0);
        user.setName(staffName);
        user.setSubject(majorName);
        user.setCollege(unitName);
        user.setClassId(classId);
        user.setNum(staffId);
        user.setPwd("670b14728ad9902aecba32e22fa4f6bd");
        return user;
    }
}
