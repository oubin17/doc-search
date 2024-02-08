package com.odk.template.util.dto;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirectoryUpdateDTO
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/2/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryUpdateDTO extends DTO {

    /**
     * 文件夹id
     */
    private String dirId;

    /**
     * 文件夹名称
     */
    private String dirName;
}
