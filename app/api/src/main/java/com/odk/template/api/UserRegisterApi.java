package com.odk.template.api;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.util.request.UserRegisterRequest;

/**
 * HelloWorldTemplate
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
public interface UserRegisterApi {

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    ServiceResponse<String> userRegister(UserRegisterRequest userRegisterRequest);

}
