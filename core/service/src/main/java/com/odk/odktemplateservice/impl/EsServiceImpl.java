package com.odk.odktemplateservice.impl;

import com.odk.odktemplatemanager.EsIndexManager;
import com.odk.odktemplatemanager.config.EsFunUtil;
import com.odk.odktemplateservice.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * EsServiceImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Service
public class EsServiceImpl implements EsService {

    private EsIndexManager esIndexManager;

    @Override
    public boolean rebuildIndex(String index) {
        try {
           return esIndexManager.rebuildIndex(index, EsFunUtil.createDocIndex(index));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setEsIndexManager(EsIndexManager esIndexManager) {
        this.esIndexManager = esIndexManager;
    }
}
