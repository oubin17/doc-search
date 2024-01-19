package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.interfaces.UserRegisterApi;
import com.odk.template.api.request.UserRegisterRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorldController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
@RestController
@RequestMapping("/user/register")
public class UserRegisterController {

    private final UserRegisterApi userRegisterApi;

    public UserRegisterController(UserRegisterApi userRegisterApi) {
        this.userRegisterApi = userRegisterApi;
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequest
     * @return
     */
    @PostMapping()
    public ServiceResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userRegisterApi.userRegister(userRegisterRequest);
    }

}
