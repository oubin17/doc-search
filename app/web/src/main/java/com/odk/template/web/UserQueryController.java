package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.interfaces.UserQueryApi;
import com.odk.template.api.request.UserQueryRequest;
import com.odk.template.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserQueryController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@RestController
@RequestMapping("/user/query")
public class UserQueryController {

    private UserQueryApi userQueryApi;

    @GetMapping("/userid")
    public ServiceResponse<UserEntity> queryUserByUserId(@RequestParam("userId") String userId) {
        return userQueryApi.queryUserByUserId(userId);
    }

    @GetMapping("/loginId")
    public ServiceResponse<UserEntity> queryUserByLoginId(@RequestParam("loginId") String loginId, @RequestParam("loginType") String loginType) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setLoginId(loginId);
        userQueryRequest.setLoginType(loginType);
        return userQueryApi.queryUserByLoginId(userQueryRequest);
    }


    @Autowired
    public void setUserQueryApi(UserQueryApi userQueryApi) {
        this.userQueryApi = userQueryApi;
    }
}
