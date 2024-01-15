package com.odk.template.util.enums;

/**
 * BizScene
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2023/11/11
 */
public enum BizScene {
    HELLO_WORLD("HELLO_WORLD", "HELLO_WORLD")

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
