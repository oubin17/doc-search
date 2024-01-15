package com.odk.template.api.impl;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.DocApi;
import com.odk.template.util.request.DocUploadRequest;
import org.springframework.stereotype.Service;

/**
 * DocApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@Service
public class DocApiImpl implements DocApi {

    @Override
    public ServiceResponse<Boolean> uploadDoc(DocUploadRequest docUploadRequest) {
        return null;
    }
}
