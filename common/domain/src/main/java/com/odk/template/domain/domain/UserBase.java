package com.odk.template.domain.domain;

import com.odk.base.dos.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * User
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserBase extends BaseDO {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户类型
     * {@link com.odk.base.enums.user.UserTypeEnum}
     */
    private String userType;

    /**
     * 用户状态
     * {@link com.odk.base.enums.user.UserStatusEnum}
     */
    private String userStatus;


}
