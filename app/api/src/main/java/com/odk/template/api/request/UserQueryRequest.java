package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * UserQueryRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends BaseRequest {

    /**
     * 登录ID
     */
    private String loginId;

    /**
     * 登录类型
     */
    private String loginType;
}
