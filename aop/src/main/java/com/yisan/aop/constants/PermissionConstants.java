package com.yisan.aop.constants;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.yisan.aop.constants.PermissionConstants.NEED_PERMISSIONS;
import static com.yisan.aop.constants.PermissionConstants.REQUEST_CODE;

/**
 * @author：wzh
 * @description:
 * @packageName: com.yisan.aop.constants
 * @date：2019/11/26 0026 下午 4:53
 */

@StringDef({NEED_PERMISSIONS, REQUEST_CODE})
@Retention(RetentionPolicy.SOURCE)
public @interface PermissionConstants {

    String NEED_PERMISSIONS = "need_permissions";
    String REQUEST_CODE = "request_code";

}
