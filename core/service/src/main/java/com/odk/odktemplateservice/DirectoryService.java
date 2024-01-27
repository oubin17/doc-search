package com.odk.odktemplateservice;

import com.odk.template.util.dto.DirectoryCreateDTO;

/**
 * DirectoryService
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
public interface DirectoryService {

    /**
     * 创建目录
     *
     * @param createDTO
     * @return
     */
    String createDirectory(DirectoryCreateDTO createDTO);

    /**
     * 删除文件夹id
     *
     * @param dirId
     * @return
     */
    boolean deleteDirectory(String dirId);
}
