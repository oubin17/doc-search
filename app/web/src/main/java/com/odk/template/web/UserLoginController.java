package com.odk.template.web;

import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.interceptor.NoLoginCondition;
import com.odk.template.api.interfaces.UserLoginApi;
import com.odk.template.api.request.UserLoginRequest;
import com.odk.template.api.response.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * UserLoginController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@RestController
@RequestMapping("/user/login")
@CrossOrigin
public class UserLoginController {

    private UserLoginApi userLoginApi;

    @NoLoginCondition
    @PostMapping()
    public ServiceResponse<UserLoginResponse> userLogin(@RequestBody UserLoginRequest loginRequest) {
        return userLoginApi.userLogin(loginRequest);
    }

    @NoLoginCondition
    @PostMapping("/a")
    public ServiceResponse<UserLoginResponse> userLoginA() {
//        int i = 10 / 0;
        throw new BizException(BizErrorCode.SYSTEM_ERROR, "123");
    }

    @Autowired
    public void setUserLoginApi(UserLoginApi userLoginApi) {
        this.userLoginApi = userLoginApi;
    }
}
