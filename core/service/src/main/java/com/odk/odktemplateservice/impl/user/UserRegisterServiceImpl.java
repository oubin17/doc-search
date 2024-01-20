package com.odk.odktemplateservice.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.odk.base.enums.user.IdentificationTypeEnum;
import com.odk.base.enums.user.UserStatusEnum;
import com.odk.base.enums.user.UserTypeEnum;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.odktemplateservice.UserRegisterService;
import com.odk.template.domain.domain.UserAccessToken;
import com.odk.template.domain.domain.UserBase;
import com.odk.template.domain.domain.UserIdentification;
import com.odk.template.domain.impl.UserRepository;
import com.odk.template.util.dto.UserRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    private UserRepository userRepository;

    private TransactionTemplate transactionTemplate;

    @Override
    public String registerUser(UserRegisterDTO userRegisterDTO) {
//        boolean exist = userRepository.checkUserExisted(userRegisterDTO.getLoginType(), userRegisterDTO.getLoginId());
//        AssertUtil.isFalse(exist, BizErrorCode.USER_HAS_EXISTED);
        String userId = UUID.randomUUID().toString();

        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    addUserBase(userId, userRegisterDTO);
                    addAccessToken(userId, userRegisterDTO);
                    addIdentification(userId, userRegisterDTO);
                }
            });
        } catch (DuplicateKeyException duplicateKeyException) {
            logger.error("注册发生唯一键冲突：{}, 异常原因：", JSONObject.toJSONString(userRegisterDTO), duplicateKeyException);
            throw new BizException(BizErrorCode.LOGIN_ID_DUPLICATE);
        } catch (Exception e) {
            logger.error("注册发生未知异常，注册信息：{}, 异常原因：", JSONObject.toJSONString(userRegisterDTO), e);
            throw new BizException(BizErrorCode.SYSTEM_ERROR);
        }

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
