package com.odk.template.domain.domain;

import com.odk.base.dos.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserAccessToken
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAccessToken extends BaseDO {

    /**
     * 主键ID
     *
     */
    private String tokenId;
    /**
     * 用户id
     */
    private String userId;

    /**
     * token 类型
     * {@link com.odk.base.enums.user.TokenTypeEnum}
     */
    private String tokenType;

    /**
     * token值
     */
    private String tokenValue;

}
