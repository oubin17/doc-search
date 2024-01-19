package com.odk.template.util.vo;

import lombok.Data;

/**
 * UserLoginVO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@Data
public class UserLoginVO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 登录token
     */
    private String token;
}
