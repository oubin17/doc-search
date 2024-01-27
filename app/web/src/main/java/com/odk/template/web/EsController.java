package com.odk.template.web;

import com.odk.base.exception.BizErrorCode;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.interceptor.NoLoginCondition;
import com.odk.template.api.interfaces.EsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EsController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@RestController
@RequestMapping("/es")
public class EsController {

    private EsApi esApi;

    @NoLoginCondition
    @PostMapping("/index/rebuild")
    public ServiceResponse<Boolean> reBuildIndex() {
        boolean rebuild = esApi.rebuildIndex();
        if (rebuild) {
            return ServiceResponse.valueOfSuccess();
        }
        return ServiceResponse.valueOfError(BizErrorCode.SYSTEM_ERROR);
    }

    @Autowired
    public void setEsApi(EsApi esApi) {
        this.esApi = esApi;
    }
}
