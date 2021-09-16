/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: Participant.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/3 下午9:05
 */

package com.hdu.honor.community.participant;

import com.hdu.honor.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "gttptcp")
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "usrid")
    private User user;
    @Column(name = "gttid")
    private Integer communityId;
    @Column(name = "typ")
    private Integer type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    public Participant(User user) {
        this.user = user;
    }
}
