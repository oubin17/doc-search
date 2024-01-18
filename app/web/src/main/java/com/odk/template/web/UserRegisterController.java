package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.UserRegisterApi;
import com.odk.template.util.request.UserRegisterRequest;
import com.odk.template.util.response.HelloWorldResponse;
import org.springframework.web.bind.annotation.*;

/**
 * HelloWorldController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
@RestController
@RequestMapping("/user")
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
    @PostMapping("/register")
    public ServiceResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        return userRegisterApi.userRegister(userRegisterRequest);
    }


    /**
     * 测试接口
     *
     * @return
     */
    @GetMapping
    public ServiceResponse<HelloWorldResponse> helloWorld() {
        HelloWorldResponse response = new HelloWorldResponse();
        response.setResult("调用到服务端啦");

        return ServiceResponse.valueOfSuccess(response);
    }

}
