package com.odk.odktemplatemanager;

import com.odk.template.util.dto.DocSearchDTO;
import org.elasticsearch.action.search.SearchResponse;

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
    SearchResponse searchDoc(String index, DocSearchDTO docSearchDTO);
}
