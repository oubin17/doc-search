package com.odk.template.util.response;

import java.util.List;

/**
 * DocSearchResponse
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public class DocSearchResponse {

    /**
     * VO
     *
     */
    private List<DocVO> result;

    public List<DocVO> getResult() {
        return result;
    }

    public void setResult(List<DocVO> result) {
        this.result = result;
    }
}
