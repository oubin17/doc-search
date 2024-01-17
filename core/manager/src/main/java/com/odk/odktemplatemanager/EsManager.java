package com.odk.odktemplatemanager;

/**
 * EsManager
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/16
 */
public interface EsManager {

    /**
     * 写数据到es
     *
     * @param docId
     * @param docName
     * @param docContents
     */
    void writeToEs(String docId, String docName, String docContents);

}
