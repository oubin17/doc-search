package com.odk.template.util.response;

import lombok.Data;

import java.util.List;

/**
 * DocSearchResponse
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Data
public class DocSearchResponse {

    /**
     * VO
     *
     */
    private List<DocVO> result;

}
