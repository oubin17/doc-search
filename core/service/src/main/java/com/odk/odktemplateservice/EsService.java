package com.odk.odktemplateservice;

/**
 * EsService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public interface EsService {

    /**
     * 重建ES索引
     *
     * @return
     */
    boolean rebuildIndex(String index);
}
