package com.odk.template.domain.interfaces;

import com.odk.template.domain.domain.UserAccessToken;
import com.odk.template.domain.domain.UserBase;
import com.odk.template.domain.domain.UserIdentification;
import com.odk.template.domain.entity.UserEntity;

/**
 * IUser
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/13
 */
public interface IUser {

    /**
     * 根据id查找
     *
     * @param id
     * @return
     */
    UserBase findOne(Integer id);


    /**
     * 检查用户是否存在
     *
     * @param tokenType
     * @param tokenValue
     * @return
     */
    boolean checkUserExisted(String tokenType, String tokenValue);

    /**
     * 查询用户
     *
     * @param userId
     * @return
     */
    UserEntity queryByUserId(String userId);

    /**
     * 添加基础表
     *
     * @param userBase
     */
    void addUserBase(UserBase userBase);

    /**
     * 添加access token
     *
     * @param accessToken
     */
    void addAccessToken(UserAccessToken accessToken);

    /**
     * 添加识别
     *
     * @param identification
     */
    void addIdentification(UserIdentification identification);

}
