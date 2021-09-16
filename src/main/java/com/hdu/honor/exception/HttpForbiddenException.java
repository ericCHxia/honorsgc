/*
 * Copyright (c) 2020-2021 杭州电子科技大学卓越学院 All Rights Reserved.
 * @ProjectName: honor
 * @FileName: HttpForbiddenException.java
 * @Author: Eric
 * @Version: 1.0
 * @LastModified: 2021/9/10 下午6:57
 */

package com.hdu.honor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Request Forbidden")
public class HttpForbiddenException  extends RuntimeException {
}
