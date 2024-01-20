package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DocDeleteRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocDeleteRequest extends BaseRequest {

    /**
     * 文件id
     */
    private String docId;
}
