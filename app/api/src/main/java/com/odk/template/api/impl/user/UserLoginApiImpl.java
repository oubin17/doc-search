package com.odk.template.api.impl.user;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.UserLoginService;
import com.odk.template.api.UserLoginApi;
import com.odk.template.util.request.UserLoginRequest;
import com.odk.template.util.response.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserLoginApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@Service
public class UserLoginApiImpl implements UserLoginApi {

    private UserLoginService userLoginService;

    @Override
    public ServiceResponse<UserLoginResponse> userLogin(UserLoginRequest loginRequest) {
        return null;
    }

    @Autowired
    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }
}
