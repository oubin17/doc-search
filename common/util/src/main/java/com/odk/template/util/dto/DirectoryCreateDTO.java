package com.odk.template.util.dto;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirectoryCreateDTO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryCreateDTO extends DTO {

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 目录名称
     */
    private String name;
}
