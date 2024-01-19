package com.odk.odktemplateservice.impl.user;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.odktemplateservice.UserLoginService;
import com.odk.template.domain.domain.UserAccessToken;
import com.odk.template.domain.domain.UserIdentification;
import com.odk.template.domain.impl.UserRepository;
import com.odk.template.util.request.UserLoginRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserLoginServiceImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/19
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    private UserRepository userRepository;

    @Override
    public boolean userLogin(UserLoginRequest userLoginRequest) {
        UserAccessToken accessToken = userRepository.queryAccessTokenByTokenValue(userLoginRequest.getLoginType(), userLoginRequest.getLoginId());
        AssertUtil.notNull(accessToken, BizErrorCode.USER_NOT_EXIST);
        UserIdentification identification = userRepository.queryIdentification(accessToken.getUserId(), userLoginRequest.getIdentifyType());
        return StringUtils.equals(userLoginRequest.getIdentifyValue(), identification.getIdentifyValue());
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
