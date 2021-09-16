/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: UserConverter.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/5 下午2:43
 */

package com.hdu.honor.user;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User> {
    @Autowired
    private UserService userService;
    @Override
    public Class supportJavaTypeKey() {
        return User.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public User convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(User user, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(String.format("%s(%s)",user.getName(),user.getNum()));
    }
}
