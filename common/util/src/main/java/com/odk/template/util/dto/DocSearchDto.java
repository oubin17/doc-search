package com.odk.template.util.dto;

import com.odk.base.dto.DTO;
import com.odk.template.util.response.DocVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * DocSearchDto
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocSearchDto extends DTO {

    private String keyword;

    private List<DocVO> docVOList;
}
