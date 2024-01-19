package com.odk.odktemplateservice;

import com.odk.template.util.dto.DocSaveDTO;
import com.odk.template.util.dto.DocSearchDTO;
import com.odk.template.util.vo.DocVO;

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
    String saveDoc(DocSaveDTO docSaveDto);

    /**
     * 文件搜索
     *
     * @param searchDto
     * @return
     */
    List<DocVO> searchDoc(DocSearchDTO searchDto);
}
