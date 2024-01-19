package com.odk.template.api;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.util.request.UserLoginRequest;
import com.odk.template.util.response.UserLoginResponse;

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
     * @param loginRequest
     * @return
     */
    ServiceResponse<UserLoginResponse> userLogin(UserLoginRequest loginRequest);
}
