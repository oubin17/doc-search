package com.odk.template.util.enums;

import com.odk.base.enums.IEnum;

/**
 * DirTypeENum
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/2/4
 */
public enum DirTypeEnum implements IEnum {

    FOLDER("1", "文件夹"),

    FILE("2", "文件")

    ;


    private final String code;

    private final String description;

    DirTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
