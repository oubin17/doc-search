package com.odk.template.api;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.util.request.DocUploadRequest;

/**
 * DocApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
public interface DocApi {

    /**
     * 文件上传API
     *
     * @param docUploadRequest
     * @return
     */
    ServiceResponse<Boolean> uploadDoc(DocUploadRequest docUploadRequest);
}
