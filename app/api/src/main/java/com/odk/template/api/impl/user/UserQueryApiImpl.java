package com.odk.template.api.impl.user;

import com.odk.base.enums.user.TokenTypeEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.request.BaseRequest;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.odktemplateservice.UserQueryService;
import com.odk.template.api.interfaces.UserQueryApi;
import com.odk.template.api.request.UserQueryRequest;
import com.odk.template.api.template.AbstractApiImpl;
import com.odk.template.domain.entity.UserEntity;
import com.odk.template.util.dto.UserQueryDTO;
import com.odk.template.util.enums.BizScene;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserQueryApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@Service
public class UserQueryApiImpl extends AbstractApiImpl implements UserQueryApi {

    private UserQueryService userQueryService;

    @Override
    public ServiceResponse<UserEntity> queryUserByUserId(String userId) {
        UserEntity userEntity = userQueryService.queryUserByUserId(userId);
        return ServiceResponse.valueOfSuccess(userEntity);
    }

    @Override
    public ServiceResponse<UserEntity> queryUserByLoginId(UserQueryRequest userQueryRequest) {
        return super.bizProcess(BizScene.USER_REGISTER, userQueryRequest, UserEntity.class, new ApiCallBack<UserEntity, UserEntity>() {

            @Override
            protected void checkParams(BaseRequest request) {
                super.checkParams(request);
                UserQueryRequest queryRequest = (UserQueryRequest) request;
                AssertUtil.notNull(queryRequest.getLoginId(), BizErrorCode.PARAM_ILLEGAL, "loginId is null.");
                AssertUtil.notNull(TokenTypeEnum.getByCode(queryRequest.getLoginType()), BizErrorCode.PARAM_ILLEGAL, "loginType is null.");
            }

            @Override
            protected Object convert(BaseRequest request) {
                UserQueryRequest queryRequest = (UserQueryRequest) request;
                UserQueryDTO userQueryDTO = new UserQueryDTO();
                BeanUtils.copyProperties(queryRequest, userQueryDTO);
                return userQueryDTO;
            }

            @Override
            protected UserEntity doProcess(Object args) {
                UserQueryDTO userQueryDTO = (UserQueryDTO) args;
                return userQueryService.queryUserByLoginId(userQueryDTO);
            }

            @Override
            protected ServiceResponse<UserEntity> assembleResult(UserEntity apiResult, Class<UserEntity> resultClazz) throws Throwable {
                ServiceResponse<UserEntity> userRegisterResponse = super.assembleResult(apiResult, resultClazz);
                userRegisterResponse.setData(apiResult);
                return userRegisterResponse;

            }
        });
    }

    @Autowired
    public void setUserQueryService(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }
}
