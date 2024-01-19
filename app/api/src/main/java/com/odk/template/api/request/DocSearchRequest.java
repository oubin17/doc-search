package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DocSearchRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocSearchRequest extends BaseRequest {

    /**
     * 关键字
     */
    private String keyword;
}
