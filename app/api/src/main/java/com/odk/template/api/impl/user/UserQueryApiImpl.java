package com.odk.template.api.impl.user;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.UserQueryService;
import com.odk.template.api.UserQueryApi;
import com.odk.template.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserQueryApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@Service
public class UserQueryApiImpl implements UserQueryApi {

    private UserQueryService userQueryService;

    @Override
    public ServiceResponse<UserEntity> queryUserByUserId(String userId) {
        UserEntity userEntity = userQueryService.queryUserByUserId(userId);
        return ServiceResponse.valueOfSuccess(userEntity);
    }

    @Autowired
    public void setUserQueryService(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }
}
