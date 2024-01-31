package com.odk.template.domain.interfaces;

import com.odk.template.domain.domain.Directory;

import java.util.List;

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
    boolean checkExistence(String dicId, String userId);

    /**
     * 创建目录
     *
     * @param directory
     */
    void createDirectory(Directory directory);

    /**
     * 删除文件夹
     *
     * @param dicId
     * @param userId
     */
    boolean deleteDirectory(String dicId, String userId);


    /**
     * 查询当前节点子节点
     *
     * @param userId
     * @param curDicId
     * @return
     */
    List<Directory> queryChildDir(String curDicId, String userId);


}
