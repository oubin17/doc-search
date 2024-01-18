package com.odk.template.api;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.domain.entity.UserEntity;

/**
 * UserQueryApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
public interface UserQueryApi {

    /**
     * 根据userId查询对象
     *
     * @param userId
     * @return
     */
    ServiceResponse<UserEntity> queryUserByUserId(String userId);
}
