package com.odk.template.api.interfaces;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.request.UserLoginRequest;
import com.odk.template.api.response.UserLoginResponse;

/**
 * UserLoginApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
public interface UserLoginApi {

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    ServiceResponse<UserLoginResponse> userLogin(UserLoginRequest userLoginRequest);
}
