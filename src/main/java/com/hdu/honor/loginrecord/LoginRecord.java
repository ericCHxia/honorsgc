/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: LoginRecord.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/8/31 下午10:31
 */

package com.hdu.honor.loginrecord;

import com.hdu.honor.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "loginrec")
public class LoginRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "usrid")
    private int userId;
    @Column(name = "meth")
    private String method;
    private String ip;
    @Column(name = "dev")
    private String ua;
    @Column(name = "succ")
    private boolean success;
    private Timestamp tim;

    public LoginRecord(int userId, String method, String ip, String ua, boolean success) {
        this.userId = userId;
        this.method = method;
        this.ip = ip;
        this.ua = ua;
        this.success = success;
        this.tim = new Timestamp(new Date().getTime());
    }
}
