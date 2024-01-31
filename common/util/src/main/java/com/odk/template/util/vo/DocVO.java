package com.odk.template.util.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DocDto
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Data
public class DocVO {

    /**
     * 文档ID
     */
    private String docId;

    /**
     * 文档名称
     */
    private String docName;

    /**
     * 文档路径
     */
    private String docPath;

    /**
     * 文件夹id
     */
    private String dirId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
