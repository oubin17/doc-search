package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserRegisterRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterRequest extends BaseRequest {

    /**
     * 登录ID
     */
    private String loginId;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

}
