/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityUser.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/27 下午11:33
 */

package com.hdu.honor.community;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CommunityUser implements Serializable {
    @Column(name = "gttid",updatable = false,insertable = false)
    private Integer gttId;

    public Integer getGttId() {
        return gttId;
    }

    public void setGttId(Integer gttId) {
        this.gttId = gttId;
    }
    @Column(name = "usrid",updatable = false,insertable = false)
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommunityUser that = (CommunityUser) o;
        return gttId.equals(that.gttId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gttId, userId);
    }
}
