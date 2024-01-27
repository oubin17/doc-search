package com.odk.template.domain.domain;

import com.odk.base.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Directory
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Directory extends BaseDomain {

    /**
     * 目录ID
     */
    private String dirId;

    /**
     * 父节点ID
     *
     */
    private String parentId;

    /**
     * 目录名称
     */
    private String dirName;

    /**
     * 用户id
     */
    private String userId;
}
