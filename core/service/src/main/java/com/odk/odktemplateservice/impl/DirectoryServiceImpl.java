package com.odk.odktemplateservice.impl;

import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.odktemplateservice.DirectoryService;
import com.odk.template.domain.domain.Directory;
import com.odk.template.domain.impl.DirectoryRepository;
import com.odk.template.util.constext.ServiceContextHolder;
import com.odk.template.util.dto.DirectoryCreateDTO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * DirectoryServiceImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
@Service
public class DirectoryServiceImpl implements DirectoryService {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);

    private DirectoryRepository directoryRepository;

    @Override
    public String createDirectory(DirectoryCreateDTO createDTO) {
        if (StringUtils.isNotEmpty(createDTO.getParentId())) {
            AssertUtil.isTrue(directoryRepository.checkExistence(createDTO.getParentId(), ServiceContextHolder.getUserId()), BizErrorCode.PARAM_ILLEGAL, "父节点不存在");
        }
        Directory directory = new Directory();
        String dirId = UUID.randomUUID().toString();
        directory.setDirId(dirId);
        directory.setParentId(createDTO.getParentId());
        directory.setDirName(createDTO.getName());
        directory.setUserId(ServiceContextHolder.getUserId());
        directoryRepository.createDirectory(directory);
        return dirId;
    }

    @Override
    public boolean deleteDirectory(String dirId) {
        boolean existence = directoryRepository.checkExistence(dirId, ServiceContextHolder.getUserId());
        if (existence) {
            directoryRepository.deleteDirectory(dirId, ServiceContextHolder.getUserId());
        }
        return true;
    }

    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }
}
