package com.odk.template.domain.domain;

import com.odk.base.domain.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Doc
 *
 * @description: 文件对象表
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Doc extends BaseDomain {

    /**
     * 文件ID
     */
    private String docId;

    /**
     * 文件名称
     */
    private String docName;

    /**
     * 文件上传路径
     */
    private String docPath;

    /**
     * 文件夹id
     */
    private String dirId;

    /**
     * 用户id
     */
    private String userId;

}
