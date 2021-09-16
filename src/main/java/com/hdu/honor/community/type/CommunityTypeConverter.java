/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: CommunityTypeConverter.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/5 下午3:01
 */

package com.hdu.honor.community.type;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class CommunityTypeConverter implements Converter<CommunityType> {
    @Override
    public Class supportJavaTypeKey() {
        return CommunityType.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public CommunityType convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return null;
    }

    @Override
    public CellData convertToExcelData(CommunityType communityType, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(communityType.getName());
    }
}
