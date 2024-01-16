package com.odk.odktemplateservice;

import com.odk.template.util.dto.DocSaveDto;

/**
 * DocService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
public interface DocService {

    /**
     * 保存文件
     */
    String saveDoc(DocSaveDto docSaveDto);
}
