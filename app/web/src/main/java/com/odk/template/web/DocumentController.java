package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.DocApi;
import com.odk.template.util.request.DocUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DocmentController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@RestController
@RequestMapping("/doc")
public class DocumentController {

    private DocApi docApi;

    @PostMapping("/upload")
    public ServiceResponse<Boolean> uploadDocument(@RequestBody DocUploadRequest docUploadRequest) {
        return ServiceResponse.valueOfSuccess(true);
    }

    @Autowired
    public void setDocApi(DocApi docApi) {
        this.docApi = docApi;
    }
}
