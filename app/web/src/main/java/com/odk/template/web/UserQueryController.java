package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.UserQueryApi;
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
@RequestMapping("/user")
public class UserQueryController {

    private UserQueryApi userQueryApi;

    @GetMapping("/query/userid")
    public ServiceResponse<UserEntity> queryUserByUserId(@RequestParam("userId") String userId) {
        return userQueryApi.queryUserByUserId(userId);
    }

    @Autowired
    public void setUserQueryApi(UserQueryApi userQueryApi) {
        this.userQueryApi = userQueryApi;
    }
}
