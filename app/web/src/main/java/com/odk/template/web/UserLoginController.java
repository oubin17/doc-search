package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.UserLoginApi;
import com.odk.template.util.request.UserLoginRequest;
import com.odk.template.util.response.UserLoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserLoginController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@RestController
@RequestMapping("/user/login")
public class UserLoginController {

    private UserLoginApi userLoginApi;

    @GetMapping()
    public ServiceResponse<UserLoginResponse> queryUserByUserId(@RequestParam() UserLoginRequest loginRequest) {
        return userLoginApi.userLogin(loginRequest);
    }

    @Autowired
    public void setUserLoginApi(UserLoginApi userLoginApi) {
        this.userLoginApi = userLoginApi;
    }
}
