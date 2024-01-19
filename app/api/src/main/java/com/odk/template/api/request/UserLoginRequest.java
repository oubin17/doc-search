package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserLoginRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLoginRequest extends BaseRequest {

    /**
     * 登录ID
     */
    private String loginId;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 密码类型
     */
    private String identifyType;

    /**
     * 密码
     */
    private String identifyValue;
}
