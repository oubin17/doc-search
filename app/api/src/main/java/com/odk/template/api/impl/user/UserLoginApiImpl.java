package com.odk.template.api.impl.user;

import com.odk.base.enums.user.IdentificationTypeEnum;
import com.odk.base.enums.user.TokenTypeEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.UserLoginService;
import com.odk.template.api.interfaces.UserLoginApi;
import com.odk.template.api.request.UserLoginRequest;
import com.odk.template.api.response.UserLoginResponse;
import com.odk.template.api.template.AbstractApiImpl;
import com.odk.template.util.dto.UserLoginDTO;
import com.odk.template.util.enums.BizScene;
import com.odk.template.util.vo.UserLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserLoginApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@Service
public class UserLoginApiImpl extends AbstractApiImpl implements UserLoginApi {

    private UserLoginService userLoginService;

    @Override
    public ServiceResponse<UserLoginResponse> userLogin(UserLoginRequest userLoginRequest) {
        return super.bizProcess(BizScene.USER_LOGIN, userLoginRequest, UserLoginResponse.class, new AbstractApiImpl.ApiCallBack<UserLoginVO, UserLoginResponse>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                UserLoginRequest loginRequest = (UserLoginRequest) request;
                AssertUtil.notNull(loginRequest.getLoginId(), BizErrorCode.PARAM_ILLEGAL, "loginId is null.");
                AssertUtil.notNull(TokenTypeEnum.getByCode(loginRequest.getLoginType()), BizErrorCode.PARAM_ILLEGAL, "loginType is null.");
                AssertUtil.notNull(IdentificationTypeEnum.getByCode(loginRequest.getIdentifyType()), BizErrorCode.PARAM_ILLEGAL, "identifyType is null.");
                AssertUtil.notNull(loginRequest.getIdentifyValue(), BizErrorCode.PARAM_ILLEGAL, "identifyValue is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                UserLoginRequest loginRequest = (UserLoginRequest) request;
                UserLoginDTO userLoginDTO = new UserLoginDTO();
                BeanUtils.copyProperties(loginRequest, userLoginDTO);
                return userLoginDTO;
            }

            @Override
            protected UserLoginVO doProcess(Object args) {
                UserLoginDTO userLoginDTO = (UserLoginDTO) args;
                return userLoginService.userLogin(userLoginDTO);
            }

            @Override
            protected ServiceResponse<UserLoginResponse> assembleResult(UserLoginVO apiResult, Class<UserLoginResponse> resultClazz) throws Throwable {
                ServiceResponse<UserLoginResponse> userRegisterResponse = super.assembleResult(apiResult, resultClazz);
                UserLoginResponse userLoginResponse = new UserLoginResponse();
                userLoginResponse.setToken(apiResult.getToken());
                userLoginResponse.setUserId(apiResult.getUserId());
                userRegisterResponse.setData(userLoginResponse);
                return userRegisterResponse;

            }
        });

    }

    @Autowired
    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }
}
