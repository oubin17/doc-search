package com.odk.template.api.impl.es;

import com.odk.odktemplateservice.EsService;
import com.odk.template.api.EsApi;
import com.odk.template.util.enums.EsIndexEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EsApiImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Service
public class EsApiImpl implements EsApi {

    private EsService esService;

    @Override
    public boolean rebuildIndex() {
        return esService.rebuildIndex(EsIndexEnum.DOC_SEARCH.getCode());
    }

    @Autowired
    public void setEsService(EsService esService) {
        this.esService = esService;
    }
}
