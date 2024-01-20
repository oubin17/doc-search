package com.odk.template.api.interfaces;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.request.DocDeleteRequest;
import com.odk.template.api.request.DocSearchRequest;
import com.odk.template.api.request.DocUploadRequest;
import com.odk.template.api.response.DocSearchResponse;

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
     * 删除文档
     *
     * @param docDeleteRequest
     * @return
     */
    ServiceResponse<Boolean> deleteDoc(DocDeleteRequest docDeleteRequest);

    /**
     * 文件搜索
     *
     * @param docSearchRequest
     * @return
     */
    ServiceResponse<DocSearchResponse> searchDoc(DocSearchRequest docSearchRequest);


}
