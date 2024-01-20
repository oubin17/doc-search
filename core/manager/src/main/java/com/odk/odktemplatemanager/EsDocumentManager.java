package com.odk.odktemplatemanager;

import org.elasticsearch.search.SearchHit;

import java.util.Map;

/**
 * EsDocumentManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public interface EsDocumentManager {


    /**
     * 数据写入es
     *
     * @param index
     * @param contents
     */
    void writeEs(String index, Map<String, Object> contents);

    /**
     * 根据es id删除文档
     *
     * @param docId
     */
    void deleteByDocId(String docId);

    /**
     * 根据字段查询
     *
     * @param index
     * @param field
     * @param value
     * @return
     */
    SearchHit[] searchByField(String index, String field, String value);
}
