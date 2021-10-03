/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Community.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 上午11:07
 */

package com.hdu.honor.community;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.hdu.honor.community.participant.Participant;
import com.hdu.honor.community.record.CommunityRecord;
import com.hdu.honor.community.type.CommunityType;
import com.hdu.honor.community.type.CommunityTypeConverter;
import com.hdu.honor.user.User;
import com.hdu.honor.user.UserConverter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Time;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Table(name = "gttdata")
@Entity
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExcelIgnore
    private Integer id;
    @ExcelProperty("名称")
    private String title;
    @ExcelProperty("描述")
    @Column(name = "dsc")
    private String description;
    @ExcelIgnore
    private String detail;
    @ExcelIgnore
    @Column(name = "stat")
    private Integer state;
    @ExcelProperty(value = "类型",converter = CommunityTypeConverter.class)
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "typ")
    private CommunityType type;
    @ExcelProperty(value = "创建人",converter = UserConverter.class)
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "usrid")
    private User user;
    @ExcelIgnore
    private String img;
    @ExcelIgnore
    private Integer lmt;
    private Date tim;
    private Boolean enrolling;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "gttid")
    private List<CommunityRecord> records;
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "gttid")
    @Where(clause = "typ = 0")
    Set<Participant> participants;
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "gttid")
    @Where(clause = "typ = 1")
    Set<Participant> managers;
    public boolean isManager(User user){
        Participant participant = new Participant(user);
        return managers.contains(participant);
    }
    public boolean isParticipant(User user){
        Participant participant = new Participant(user);
        return participants.contains(participant);
    }
    public void removeManager(User user){
        Participant participant = new Participant(user);
        managers.remove(participant);
    }
    public void removeParticipant(User user){
        Participant participant = new Participant(user);
        participants.remove(participant);
    }
    public Community(String title, String description, String detail,
                     CommunityType type, User user, String img, Integer lmt) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.type = type;
        this.user = user;
        this.img = img;
        this.lmt = lmt;
        this.tim = new Date();
    }

    public Community(){
        this.tim = new Date();
        this.state = 0;
        this.enrolling = true;
    }
}