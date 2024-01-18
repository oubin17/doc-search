package com.odk.odktemplateservice;

import com.odk.template.util.dto.UserRegisterDTO;

/**
 * HelloWorldService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
public interface UserRegisterService {

    /**
     * 用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    String registerUser(UserRegisterDTO userRegisterDTO);
}
