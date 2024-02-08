package com.odk.template.util.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DirectoryTreeVO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/29
 */
@Data
public class DirectoryTreeVO {
    /**
     * 目录ID
     */
    private String id;

    /**
     * 父节点ID
     *
     */
    private String parentId;

    /**
     * 目录名称
     */
    private String label;

    /**
     * 目录类型
     */
    private String dirType;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 子节点
     */
    private List<DirectoryTreeVO> childDirs;
}
