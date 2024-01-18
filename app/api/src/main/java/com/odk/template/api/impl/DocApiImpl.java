package com.odk.template.api.impl;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.DocService;
import com.odk.template.api.DocApi;
import com.odk.template.api.template.AbstractApiImpl;
import com.odk.template.util.dto.DocSaveDto;
import com.odk.template.util.dto.DocSearchDto;
import com.odk.template.util.enums.BizScene;
import com.odk.template.util.request.DocSearchRequest;
import com.odk.template.util.request.DocUploadRequest;
import com.odk.template.util.response.DocSearchResponse;
import com.odk.template.util.response.DocUploadResponse;
import com.odk.template.util.response.DocVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        return super.bizProcess(BizScene.DOC_SEARCH, docSearchRequest, DocSearchResponse.class, new ApiCallBack<DocSearchDto, DocSearchResponse>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                DocSearchRequest searchRequest = (DocSearchRequest) request;
                AssertUtil.notNull(searchRequest.getKeyword(), BizErrorCode.PARAM_ILLEGAL, "request is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DocSearchRequest searchRequest = (DocSearchRequest) request;
                DocSearchDto dto = new DocSearchDto();
                dto.setKeyword(searchRequest.getKeyword());
                return dto;
            }

            @Override
            protected ServiceResponse<DocSearchDto> doProcess(Object args) {
                DocSearchDto dto = (DocSearchDto) args;
                List<DocVO> docVOS = docService.searchDoc(dto);
                dto.setDocVOList(docVOS);
                return ServiceResponse.valueOfSuccess(dto);
            }

            @Override
            protected ServiceResponse<DocSearchResponse> assembleResult(ServiceResponse<DocSearchDto> apiResult, Class<DocSearchResponse> resultClazz) throws Throwable {
                ServiceResponse<DocSearchResponse> docSearchResponseServiceResponse = super.assembleResult(apiResult, resultClazz);
                DocSearchDto docVOS = apiResult.getData();
                DocSearchResponse response = new DocSearchResponse();
                response.setResult(docVOS.getDocVOList());
                docSearchResponseServiceResponse.setData(response);
                return docSearchResponseServiceResponse;

            }
        });

    }

    @Override
    public ServiceResponse<DocUploadResponse> uploadDoc(DocUploadRequest docUploadRequest) {
        return super.bizProcess(BizScene.DOC_UPLOAD, docUploadRequest, DocUploadResponse.class, new ApiCallBack<DocSaveDto, DocUploadResponse>() {
            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                DocUploadRequest uploadRequest = (DocUploadRequest) request;
                AssertUtil.notNull(uploadRequest.getName(), BizErrorCode.PARAM_ILLEGAL, "request is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DocUploadRequest uploadRequest = (DocUploadRequest) request;
                DocSaveDto dto = new DocSaveDto();
                dto.setDocName(uploadRequest.getName());
                dto.setFileInputStream(uploadRequest.getFileInputStream());
                return dto;
            }

            @Override
            protected ServiceResponse<DocSaveDto> doProcess(Object args) {
                DocSaveDto dto = (DocSaveDto) args;
                dto.setDocId(docService.saveDoc(dto));
                return ServiceResponse.valueOfSuccess(dto);
            }

            @Override
            protected ServiceResponse<DocUploadResponse> assembleResult(ServiceResponse<DocSaveDto> apiResult, Class<DocUploadResponse> resultClazz) throws Throwable {
                ServiceResponse<DocUploadResponse> docUploadResponseServiceResponse = super.assembleResult(apiResult, resultClazz);
                DocSaveDto date = apiResult.getData();
                DocUploadResponse response = new DocUploadResponse();
                response.setResult(date.getDocId());
                docUploadResponseServiceResponse.setData(response);
                return docUploadResponseServiceResponse;

            }
        });
    }

    @Autowired
    public void setDocService(DocService docService) {
        this.docService = docService;
    }
}
