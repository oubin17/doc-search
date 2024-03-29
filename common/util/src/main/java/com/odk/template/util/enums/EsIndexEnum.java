package com.odk.template.util.enums;

/**
 * EsIndexEnum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public enum EsIndexEnum {

    DOC_SEARCH("doc_search", "文件索引")

    ;
    private final String code;

    private final String description;

    EsIndexEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
