package com.odk.odktemplateservice.impl;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.domain.domain.User;
import com.odk.template.domain.impl.UserRepository;
import com.odk.odktemplatemanager.HelloWorldManager;
import com.odk.odktemplateservice.HelloWorldService;
import com.odk.template.util.dto.HelloWorldDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HelloWorldServiceImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/10
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    @Autowired
    private HelloWorldManager helloWorldManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceResponse<HelloWorldDto> helloWorld(HelloWorldDto dto) {
        User one;
        one = userRepository.findOne(Integer.valueOf(dto.getName()));
        AssertUtil.notNull(one, BizErrorCode.PARAM_ILLEGAL, "参数非法");
        dto.setName("Hello World: " + one.getName());
        return ServiceResponse.valueOfSuccess(dto);
    }
}
