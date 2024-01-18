package com.odk.template.domain.domain;

import com.odk.base.dos.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserIdentification
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserIdentification extends BaseDO {

    /**
     * 主键ID
     */
    private String identifyId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 认证类型
     * {@link com.odk.base.enums.user.IdentificationTypeEnum}
     */
    private String identifyType;

    /**
     * 认证值
     */
    private String identifyValue;

}
