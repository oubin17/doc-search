package com.odk.template.util.dto;

import com.odk.base.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DocSearchDto
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocSearchDTO extends PageDTO {

    private String keyword;

}
