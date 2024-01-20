package com.odk.odktemplateservice.impl.user;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.odktemplateservice.UserLoginService;
import com.odk.template.domain.domain.UserAccessToken;
import com.odk.template.domain.domain.UserIdentification;
import com.odk.template.domain.impl.UserRepository;
import com.odk.template.util.constext.SessionHolder;
import com.odk.template.util.dto.UserLoginDTO;
import com.odk.template.util.vo.UserLoginVO;
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
    public UserLoginVO userLogin(UserLoginDTO userLoginDTO) {
        UserAccessToken accessToken = userRepository.queryAccessTokenByTokenValue(userLoginDTO.getLoginType(), userLoginDTO.getLoginId());
        AssertUtil.notNull(accessToken, BizErrorCode.USER_NOT_EXIST);
        UserIdentification identification = userRepository.queryIdentification(accessToken.getUserId(), userLoginDTO.getIdentifyType());
        AssertUtil.isTrue(StringUtils.equals(userLoginDTO.getIdentifyValue(), identification.getIdentifyValue()), BizErrorCode.IDENTIFICATION_NOT_MATCH);
        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(accessToken.getUserId());
        SessionHolder.createSession(accessToken.getUserId());
        userLoginVO.setToken(SessionHolder.createSession(accessToken.getUserId()));
        return userLoginVO;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
