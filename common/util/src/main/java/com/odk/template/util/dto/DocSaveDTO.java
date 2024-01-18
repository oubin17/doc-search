package com.odk.template.util.dto;

import com.odk.base.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

/**
 * DocSaveDto
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocSaveDTO extends DTO {

    /**
     * 文件id
     */
    private String docId;

    /**
     * 文件名称
     */
    private String docName;

    /**
     * 文件流
     */
    private InputStream fileInputStream;


}
