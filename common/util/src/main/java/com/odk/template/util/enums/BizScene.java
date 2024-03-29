package com.odk.template.util.enums;

/**
 * BizScene
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public enum BizScene {

    USER_REGISTER("USER_REGISTER", "用户注册"),

    USER_LOGIN("USER_LOGIN", "用户登录"),

    HELLO_WORLD("HELLO_WORLD", "HELLO_WORLD"),

    DOC_UPLOAD("DOC_UPLOAD", "文件上传"),

    DOC_DELETE("DOC_DELETE", "文档删除"),

    DOC_SEARCH("DOC_SEARCH", "文件搜索"),

    DIRECTORY_CREATE("DIRECTORY_CREATE", "创建目录"),

    DIRECTORY_UPDATE("DIRECTORY_UPDATE", "更新目录名"),

    ;


    private final String code;

    private final String description;

    BizScene(String code, String description) {

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
