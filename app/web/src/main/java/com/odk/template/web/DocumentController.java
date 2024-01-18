package com.odk.template.web;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.DocApi;
import com.odk.template.util.request.DocSearchRequest;
import com.odk.template.util.request.DocUploadRequest;
import com.odk.template.util.response.DocSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ServiceResponse<String> uploadDocument(MultipartFile file) throws IOException {
        DocUploadRequest docUploadRequest = new DocUploadRequest();
        docUploadRequest.setFileInputStream(file.getInputStream());
        docUploadRequest.setName(file.getOriginalFilename());
        docUploadRequest.setContentType(file.getContentType());
        docUploadRequest.setFileSize(file.getSize() / 1024 + "K");

        return docApi.uploadDoc(docUploadRequest);
    }

    @GetMapping("/search")
    public ServiceResponse<DocSearchResponse> searchDoc(@RequestParam("keyword") String keyword) {
        DocSearchRequest searchRequest = new DocSearchRequest();
        searchRequest.setKeyword(keyword);
        return docApi.searchDoc(searchRequest);

    }

    @Autowired
    public void setDocApi(DocApi docApi) {
        this.docApi = docApi;
    }
}
