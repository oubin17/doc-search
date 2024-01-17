package com.odk.template.api;

/**
 * EsApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public interface EsApi {

    /**
     * 重建es索引
     *
     * @return
     */
    boolean rebuildIndex();
}
