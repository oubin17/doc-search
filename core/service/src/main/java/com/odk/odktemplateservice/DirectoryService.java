package com.odk.odktemplateservice;

import com.odk.template.util.dto.DirectoryCreateDTO;
import com.odk.template.util.dto.DirectoryUpdateDTO;
import com.odk.template.util.vo.DirectoryTreeVO;

import java.util.List;

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
     * 更新文件夹名称
     *
     * @param directoryUpdateDTO
     * @return
     */
    Boolean updateDirectory(DirectoryUpdateDTO directoryUpdateDTO);

    /**
     * 删除文件夹id
     *
     * @param dirId
     * @return
     */
    boolean deleteDirectory(String dirId);

    /**
     * 目录树
     *
     * @return
     */
    List<DirectoryTreeVO> directoryTree();
}
