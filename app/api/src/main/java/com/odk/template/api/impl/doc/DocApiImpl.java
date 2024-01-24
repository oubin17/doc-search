package com.odk.template.api.impl.doc;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.PageResponse;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.DocService;
import com.odk.template.api.interfaces.DocApi;
import com.odk.template.api.request.DocDeleteRequest;
import com.odk.template.api.request.DocSearchRequest;
import com.odk.template.api.request.DocUploadRequest;
import com.odk.template.api.response.DocSearchResponse;
import com.odk.template.api.template.AbstractApiImpl;
import com.odk.template.util.dto.DocSaveDTO;
import com.odk.template.util.dto.DocSearchDTO;
import com.odk.template.util.enums.BizScene;
import com.odk.template.util.vo.DocVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DocApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@Service
public class DocApiImpl extends AbstractApiImpl implements DocApi {

    private DocService docService;

    @Override
    public ServiceResponse<DocSearchResponse> searchDoc(DocSearchRequest docSearchRequest) {

        return super.bizProcess(BizScene.DOC_SEARCH, docSearchRequest, DocSearchResponse.class, new ApiCallBack<PageResponse<DocVO>, DocSearchResponse>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                DocSearchRequest searchRequest = (DocSearchRequest) request;
                AssertUtil.notNull(searchRequest.getKeyword(), BizErrorCode.PARAM_ILLEGAL, "request is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DocSearchRequest searchRequest = (DocSearchRequest) request;
                DocSearchDTO dto = new DocSearchDTO();
                BeanUtils.copyProperties(searchRequest, dto);
                return dto;
            }

            @Override
            protected PageResponse<DocVO> doProcess(Object args) {
                DocSearchDTO dto = (DocSearchDTO) args;
                return docService.searchDoc(dto);

            }

            @Override
            protected ServiceResponse<DocSearchResponse> assembleResult(PageResponse<DocVO> apiResult, Class<DocSearchResponse> resultClazz) throws Throwable {
                ServiceResponse<DocSearchResponse> docSearchResponseServiceResponse = super.assembleResult(apiResult, resultClazz);
                DocSearchResponse docSearchResponse = new DocSearchResponse();
                docSearchResponse.setResult(apiResult.getPageList());
                docSearchResponse.setCount(apiResult.getCount());
                docSearchResponseServiceResponse.setData(docSearchResponse);
                return docSearchResponseServiceResponse;

            }
        });

    }

    @Override
    public ServiceResponse<Boolean> deleteDoc(DocDeleteRequest docDeleteRequest) {
        return super.bizProcess(BizScene.DOC_DELETE, docDeleteRequest, Boolean.class, new ApiCallBack<String, Boolean>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                DocDeleteRequest deleteRequest = (DocDeleteRequest) request;
                AssertUtil.notNull(deleteRequest.getDocId(), BizErrorCode.PARAM_ILLEGAL, "docId is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DocDeleteRequest deleteRequest = (DocDeleteRequest) request;
                return deleteRequest.getDocId();
            }

            @Override
            protected String doProcess(Object args) {
                String docId = (String) args;
                return docService.deleteDoc(docId);
            }

            @Override
            protected ServiceResponse<Boolean> assembleResult(String apiResult, Class<Boolean> resultClazz) throws Throwable {
                ServiceResponse<Boolean> deleteResponse = super.assembleResult(apiResult, resultClazz);
                deleteResponse.setData(true);
                return deleteResponse;

            }
        });
    }

    @Override
    public ServiceResponse<String> uploadDoc(DocUploadRequest docUploadRequest) {
        return super.bizProcess(BizScene.DOC_UPLOAD, docUploadRequest, String.class, new ApiCallBack<String, String>() {
            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                DocUploadRequest uploadRequest = (DocUploadRequest) request;
                AssertUtil.notNull(uploadRequest.getName(), BizErrorCode.PARAM_ILLEGAL, "request is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DocUploadRequest uploadRequest = (DocUploadRequest) request;
                DocSaveDTO dto = new DocSaveDTO();
                dto.setDocName(uploadRequest.getName());
                dto.setFileInputStream(uploadRequest.getFileInputStream());
                return dto;
            }

            @Override
            protected String doProcess(Object args) {
                DocSaveDTO dto = (DocSaveDTO) args;
                return docService.saveDoc(dto);
            }

            @Override
            protected ServiceResponse<String> assembleResult(String apiResult, Class<String> resultClazz) throws Throwable {
                ServiceResponse<String> docUploadResponseServiceResponse = super.assembleResult(apiResult, resultClazz);
                docUploadResponseServiceResponse.setData(apiResult);
                return docUploadResponseServiceResponse;

            }
        });
    }

    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }
}
