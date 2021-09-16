/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: InvalidParameterException.java
 * @Author: Eric
 * @Date: 2021/8/31 下午9:10
 * @Version: 1.0
 * @LastModified: 2021/8/31 下午9:10
 */

package com.hdu.honor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid parameter")
public class HttpInvalidParameterException extends RuntimeException{
}
