package com.odk.odktemplateservice;

import com.odk.template.util.request.UserLoginRequest;

/**
 * UserLoginService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
public interface UserLoginService {

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @return
     */
    boolean userLogin(UserLoginRequest userLoginRequest);
}
