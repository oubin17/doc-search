package com.odk.template.api;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.util.request.DocSearchRequest;
import com.odk.template.util.request.DocUploadRequest;
import com.odk.template.util.response.DocSearchResponse;

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
    ServiceResponse<String> uploadDoc(DocUploadRequest docUploadRequest);

    /**
     * 文件搜索
     *
     * @param docSearchRequest
     * @return
     */
    ServiceResponse<DocSearchResponse> searchDoc(DocSearchRequest docSearchRequest);
}
