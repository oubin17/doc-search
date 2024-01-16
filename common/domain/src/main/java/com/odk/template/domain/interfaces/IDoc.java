package com.odk.template.domain.interfaces;

import com.odk.template.domain.domain.Doc;

/**
 * IDoc
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
public interface IDoc {

    /**
     * 保存文件
     *
     * @param doc
     */
    void saveDoc(Doc doc);
}
