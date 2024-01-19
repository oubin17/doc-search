package com.odk.template.api.impl.user;

import com.odk.base.enums.user.TokenTypeEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.UserRegisterService;
import com.odk.template.api.interfaces.UserRegisterApi;
import com.odk.template.api.template.AbstractApiImpl;
import com.odk.template.util.dto.UserRegisterDTO;
import com.odk.template.util.enums.BizScene;
import com.odk.template.api.request.UserRegisterRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * HelloWorldTemplateImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
@Service
public class UserRegisterApiImpl extends AbstractApiImpl implements UserRegisterApi {

    private final UserRegisterService userRegisterService;

    public UserRegisterApiImpl(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @Override
    public ServiceResponse<String> userRegister(UserRegisterRequest userRegisterRequest) {
        return super.bizProcess(BizScene.USER_REGISTER, userRegisterRequest, String.class, new ApiCallBack<String, String>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                UserRegisterRequest registerRequest = (UserRegisterRequest) request;
                AssertUtil.notNull(registerRequest.getLoginId(), BizErrorCode.PARAM_ILLEGAL, "loginId is null.");
                AssertUtil.notNull(TokenTypeEnum.getByCode(registerRequest.getLoginType()), BizErrorCode.PARAM_ILLEGAL, "loginType is null.");
                AssertUtil.isTrue(StringUtils.isNotEmpty(registerRequest.getUserName()), BizErrorCode.PARAM_ILLEGAL, "userName is null.");
                AssertUtil.notNull(registerRequest.getPassword(), BizErrorCode.PARAM_ILLEGAL, "password is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                UserRegisterRequest registerRequest = (UserRegisterRequest) request;
                UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
                BeanUtils.copyProperties(registerRequest, userRegisterDTO);
                return userRegisterDTO;
            }

            @Override
            protected String doProcess(Object args) {
                UserRegisterDTO userRegisterDTO = (UserRegisterDTO) args;
                return userRegisterService.registerUser(userRegisterDTO);
            }

            @Override
            protected ServiceResponse<String> assembleResult(String apiResult, Class<String> resultClazz) throws Throwable {
                ServiceResponse<String> userRegisterResponse = super.assembleResult(apiResult, resultClazz);
                userRegisterResponse.setData(apiResult);
                return userRegisterResponse;

            }
        });


    }

}
