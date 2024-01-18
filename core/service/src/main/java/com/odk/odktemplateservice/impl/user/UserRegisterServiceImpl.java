package com.odk.odktemplateservice.impl.user;

import com.odk.base.enums.user.IdentificationTypeEnum;
import com.odk.base.enums.user.UserStatusEnum;
import com.odk.base.enums.user.UserTypeEnum;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.odktemplateservice.UserRegisterService;
import com.odk.template.domain.domain.UserAccessToken;
import com.odk.template.domain.domain.UserBase;
import com.odk.template.domain.domain.UserIdentification;
import com.odk.template.domain.impl.UserRepository;
import com.odk.template.util.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.UUID;

/**
 * UserRegisterServiceImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/18
 */
@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    private UserRepository userRepository;

    private TransactionTemplate transactionTemplate;

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
        boolean exist = userRepository.checkUserExisted(userRegisterDTO.getLoginType(), userRegisterDTO.getLoginId());
//        UserBase one = userRepository.findOne(12445);
        AssertUtil.isFalse(exist, BizErrorCode.USER_HAS_EXISTED);
        String userId = UUID.randomUUID().toString();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                addUserBase(userId, userRegisterDTO);
                addAccessToken(userId, userRegisterDTO);
                addIdentification(userId, userRegisterDTO);
            }
        });

        return userId;
    }

    /**
     * 添加基础信息
     *
     * @param userId
     * @param userRegisterDTO
     */
    private void addUserBase(String userId, UserRegisterDTO userRegisterDTO) {
        UserBase userBase = new UserBase();
        userBase.setUserId(userId);
        userBase.setUserType(UserTypeEnum.INDIVIDUAL.getCode());
        userBase.setUserStatus(UserStatusEnum.NORMAL.getCode());
        userBase.setUserName(userRegisterDTO.getUserName());
        userRepository.addUserBase(userBase);
    }

    /**
     * 添加登录手机号
     *
     * @param userId
     * @param userRegisterDTO
     */
    private void addAccessToken(String userId, UserRegisterDTO userRegisterDTO) {
        UserAccessToken accessToken = new UserAccessToken();
        accessToken.setTokenId(UUID.randomUUID().toString());
        accessToken.setUserId(userId);
        accessToken.setTokenType(userRegisterDTO.getLoginType());
        accessToken.setTokenValue(userRegisterDTO.getLoginId());
        userRepository.addAccessToken(accessToken);
    }

    /**
     * 添加密码
     *
     * @param userId
     * @param userRegisterDTO
     */
    private void addIdentification(String userId, UserRegisterDTO userRegisterDTO) {
        UserIdentification identification = new UserIdentification();
        identification.setIdentifyId(UUID.randomUUID().toString());
        identification.setUserId(userId);
        identification.setIdentifyType(IdentificationTypeEnum.PASSWORD.getCode());
        identification.setIdentifyValue(userRegisterDTO.getPassword());
        userRepository.addIdentification(identification);
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
}
