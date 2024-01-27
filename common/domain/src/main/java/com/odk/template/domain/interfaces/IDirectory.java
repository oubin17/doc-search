package com.odk.template.domain.interfaces;

import com.odk.template.domain.domain.Directory;

/**
 * DirectoryRepository
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
public interface IDirectory {

    /**
     * 检查节点是否存在
     *
     * @param dicId
     * @return
     */
    boolean checkExistence(String dicId);

    /**
     * 创建目录
     *
     * @param directory
     */
    void createDirectory(Directory directory);


}
