package com.odk.template.api;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.util.request.DocUploadRequest;
import com.odk.template.util.response.DocUploadResponse;

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
    ServiceResponse<DocUploadResponse> uploadDoc(DocUploadRequest docUploadRequest);
}
