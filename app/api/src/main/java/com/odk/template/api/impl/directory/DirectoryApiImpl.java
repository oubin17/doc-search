package com.odk.template.api.impl.directory;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.DirectoryService;
import com.odk.template.api.interfaces.DirectoryApi;
import com.odk.template.api.request.DirectoryCreateRequest;
import com.odk.template.api.request.DirectoryUpdateRequest;
import com.odk.template.api.template.AbstractApiImpl;
import com.odk.template.util.dto.DirectoryCreateDTO;
import com.odk.template.util.dto.DirectoryUpdateDTO;
import com.odk.template.util.enums.BizScene;
import com.odk.template.util.vo.DirectoryTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * DirectoryApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
@Service
public class DirectoryApiImpl extends AbstractApiImpl implements DirectoryApi {

    private DirectoryService directoryService;


    @Override
    public ServiceResponse<String> createDirectory(DirectoryCreateRequest directoryCreateRequest) {
        return super.bizProcess(BizScene.DIRECTORY_CREATE, directoryCreateRequest, String.class, new ApiCallBack<String, String>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                AssertUtil.notNull(directoryCreateRequest.getName(), BizErrorCode.PARAM_ILLEGAL, "name is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DirectoryCreateRequest createRequest = (DirectoryCreateRequest) request;
                DirectoryCreateDTO dto = new DirectoryCreateDTO();
                BeanUtils.copyProperties(createRequest, dto);
                return dto;
            }

            @Override
            protected String doProcess(Object args) {
                DirectoryCreateDTO dto = (DirectoryCreateDTO) args;
                return directoryService.createDirectory(dto);
            }

            @Override
            protected ServiceResponse<String> assembleResult(String apiResult, Class<String> resultClazz) throws Throwable {
                ServiceResponse<String> response = super.assembleResult(apiResult, resultClazz);
                response.setData(apiResult);
                return response;
            }
        });
    }

    @Override
    public ServiceResponse<Boolean> updateDirectory(DirectoryUpdateRequest directoryUpdateRequest) {
        return super.bizProcess(BizScene.DIRECTORY_UPDATE, directoryUpdateRequest, Boolean.class, new ApiCallBack<Boolean, Boolean>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                AssertUtil.notNull(directoryUpdateRequest.getDirId(), BizErrorCode.PARAM_ILLEGAL, "dirId is null.");
                AssertUtil.notNull(directoryUpdateRequest.getDirName(), BizErrorCode.PARAM_ILLEGAL, "dirName is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                DirectoryUpdateRequest updateRequest = (DirectoryUpdateRequest) request;
                DirectoryUpdateDTO dto = new DirectoryUpdateDTO();
                BeanUtils.copyProperties(updateRequest, dto);
                return dto;
            }

            @Override
            protected Boolean doProcess(Object args) {
                DirectoryUpdateDTO dto = (DirectoryUpdateDTO) args;
                return directoryService.updateDirectory(dto);
            }

            @Override
            protected ServiceResponse<Boolean> assembleResult(Boolean apiResult, Class<Boolean> resultClazz) throws Throwable {
                if (apiResult) {
                    return ServiceResponse.valueOfSuccess();
                } else  {
                    return ServiceResponse.valueOfError(BizErrorCode.SYSTEM_ERROR);
                }

            }
        });
    }

    @Override
    public boolean deleteDirectory(String dirId) {
        return directoryService.deleteDirectory(dirId);
    }

    @Override
    public ServiceResponse<List<DirectoryTreeVO>> directoryTree() {
        return ServiceResponse.valueOfSuccess(directoryService.directoryTree());
    }

    @Autowired
    public void setDirectoryService(DirectoryService directoryService) {
        this.directoryService = directoryService;
    }
}
