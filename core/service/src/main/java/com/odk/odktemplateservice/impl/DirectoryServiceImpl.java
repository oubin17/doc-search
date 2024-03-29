package com.odk.odktemplateservice.impl;

import com.google.common.collect.Lists;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.odktemplateservice.DirectoryService;
import com.odk.template.domain.domain.Directory;
import com.odk.template.domain.domain.Doc;
import com.odk.template.domain.impl.DirectoryRepository;
import com.odk.template.domain.impl.DocRepository;
import com.odk.template.util.constext.ServiceContextHolder;
import com.odk.template.util.dto.DirectoryCreateDTO;
import com.odk.template.util.dto.DirectoryUpdateDTO;
import com.odk.template.util.enums.DirTypeEnum;
import com.odk.template.util.vo.DirectoryTreeVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private DocRepository docRepository;

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
    public Boolean updateDirectory(DirectoryUpdateDTO directoryUpdateDTO) {
        boolean existence = directoryRepository.checkExistence(directoryUpdateDTO.getDirId(), ServiceContextHolder.getUserId());
        AssertUtil.isTrue(existence, BizErrorCode.PARAM_ILLEGAL, "文件夹不存在");
        return directoryRepository.updateDirectory(directoryUpdateDTO.getDirName(), directoryUpdateDTO.getDirId(), ServiceContextHolder.getUserId()) > 0;
    }

    @Override
    public boolean deleteDirectory(String dirId) {
        boolean existence = directoryRepository.checkExistence(dirId, ServiceContextHolder.getUserId());
        if (existence) {
            directoryRepository.deleteDirectory(dirId, ServiceContextHolder.getUserId());
        }
        return true;
    }

    @Override
    public List<DirectoryTreeVO> directoryTree() {
        return buildDirectoryTree(null);
    }

    /**
     * 返回目录树
     *
     * @param curDir
     * @return
     */
    private List<DirectoryTreeVO> buildDirectoryTree(String curDir) {

        List<Directory> curDirList = directoryRepository.queryChildDir(curDir, ServiceContextHolder.getUserId());
        List<DirectoryTreeVO> treeVOS = convertDirectoryToTree(curDirList);
        List<Doc> docs = docRepository.queryDocByDirId(curDir, ServiceContextHolder.getUserId());
        treeVOS.addAll(convertDocToTree(docs, curDir));
        if (CollectionUtils.isEmpty(treeVOS)) {
            return Lists.newArrayList();
        }
        for (DirectoryTreeVO dir : treeVOS) {
            if (StringUtils.equals(DirTypeEnum.FOLDER.getCode(), dir.getDirType())) {
                List<DirectoryTreeVO> childDir = buildDirectoryTree(dir.getId());
                dir.setChildDirs(childDir);
            }
        }
        return treeVOS;
    }

    /**
     * 文件改成tree
     *
     * @param docList
     * @return
     */
    private List<DirectoryTreeVO> convertDocToTree(List<Doc> docList, String curDir) {
        return docList.stream().map(doc -> {
            DirectoryTreeVO directoryTreeVO = new DirectoryTreeVO();
            BeanUtils.copyProperties(doc, directoryTreeVO);
            directoryTreeVO.setId(doc.getDocId());
            directoryTreeVO.setParentId(curDir);
            directoryTreeVO.setLabel(doc.getDocName());
            directoryTreeVO.setDirType(DirTypeEnum.FILE.getCode());
            return directoryTreeVO;
        }).collect(Collectors.toList());

    }


    /**
     * DO -> VO
     *
     * @param directoryList
     * @return
     */
    private List<DirectoryTreeVO> convertDirectoryToTree(List<Directory> directoryList) {
        return directoryList.stream().map(directory -> {
            DirectoryTreeVO directoryTreeVO = new DirectoryTreeVO();
            BeanUtils.copyProperties(directory, directoryTreeVO);
            directoryTreeVO.setId(directory.getDirId());
            directoryTreeVO.setLabel(directory.getDirName());
            directoryTreeVO.setDirType(DirTypeEnum.FOLDER.getCode());
            return directoryTreeVO;
        }).collect(Collectors.toList());
    }
    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }

    @Autowired
    public void setDocRepository(DocRepository docRepository) {
        this.docRepository = docRepository;
    }
}
