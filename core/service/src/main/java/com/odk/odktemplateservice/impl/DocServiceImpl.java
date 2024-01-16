package com.odk.odktemplateservice.impl;

import com.odk.odktemplateservice.DocService;
import com.odk.template.domain.domain.Doc;
import com.odk.template.domain.impl.DocRepository;
import com.odk.template.util.common.FileUtil;
import com.odk.template.util.dto.DocSaveDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * DocServiceImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@Service
public class DocServiceImpl implements DocService {

    private static final Logger logger = LoggerFactory.getLogger(DocServiceImpl.class);

    @Value("${file.input.path}")
    private String filePath;

    private DocRepository docRepository;

    @Override
    public String saveDoc(DocSaveDto docSaveDto) {
        String docId = UUID.randomUUID().toString();
        //1.上传文件
        try {
            FileUtil.saveFile(filePath, docId, docSaveDto.getDocName(), docSaveDto.getFileInputStream());
        } catch (IOException e) {
            logger.error("文件上传失败，文件名：{}", docSaveDto.getDocName());
            throw new RuntimeException(e);
        }
        //2.保存文件到db
        Doc doc = new Doc();
        doc.setDocId(docId);
        doc.setDocName(docSaveDto.getDocName());
        docRepository.saveDoc(doc);
        //3.上传到ES

        return docId;
    }

    @Autowired
    public void setDocRepository(DocRepository docRepository) {
        this.docRepository = docRepository;
    }
}
