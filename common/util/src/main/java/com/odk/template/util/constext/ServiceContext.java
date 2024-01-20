package com.odk.template.util.constext;

import lombok.Data;

import java.io.Serializable;

/**
 * ServiceContext
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
@Data
public class ServiceContext implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 租户ID
     */
    private String tenantId;
}
