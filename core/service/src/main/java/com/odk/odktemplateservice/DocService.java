package com.odk.odktemplateservice;

import com.odk.template.util.dto.DocSaveDto;
import com.odk.template.util.dto.DocSearchDto;
import com.odk.template.util.response.DocVO;

import java.util.List;

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

    /**
     * 文件搜索
     *
     * @param searchDto
     * @return
     */
    List<DocVO> searchDoc(DocSearchDto searchDto);
}
