package com.odk.odktemplatemanager;

import org.elasticsearch.client.indices.CreateIndexRequest;

/**
 * EsIndexManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public interface EsIndexManager {

    /**
     * 重建es索引
     * @param index
     * @return
     */
    boolean rebuildIndex(String index, CreateIndexRequest createIndexRequest);


    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    boolean existsIndex(String index);

    /**
     * 创建索引
     *
     * @param index
     */
    void createIndex(String index, CreateIndexRequest createIndexRequest);

    /**
     * 删除索引
     *
     * @param index
     */
    void deleteIndex(String index);
}
