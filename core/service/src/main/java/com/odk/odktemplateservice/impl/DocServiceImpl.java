package com.odk.odktemplateservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.odktemplatemanager.EsDocumentManager;
import com.odk.odktemplatemanager.util.FileUtil;
import com.odk.odktemplateservice.DocService;
import com.odk.template.domain.domain.Doc;
import com.odk.template.domain.impl.DocRepository;
import com.odk.template.util.dto.DocSaveDTO;
import com.odk.template.util.dto.DocSearchDTO;
import com.odk.template.util.enums.EsIndexEnum;
import com.odk.template.util.vo.DocVO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    private EsDocumentManager esDocumentManager;

    @Override
    public String saveDoc(DocSaveDTO docSaveDto) {
        String docId = UUID.randomUUID().toString();
        try {
            //1.上传文件
            FileUtil.saveFile(filePath, docId, docSaveDto.getDocName(), docSaveDto.getFileInputStream());
            //2.获取文件内容
            String docContents = FileUtil.getDocContents(filePath, docId, docSaveDto.getDocName());
            logger.info("文件内容 {}", docContents);
            if (StringUtils.isNotEmpty(docContents)) {
                //3.内容写到ES
                Map<String, Object> content = new HashMap<>();
                content.put("docId", docId);
                content.put("docName", docSaveDto.getDocName());
                content.put("docContents", docContents);
                esDocumentManager.writeEs(EsIndexEnum.DOC_SEARCH.getCode(), content);
            }
        } catch (IOException e) {
            logger.error("文件上传失败，文件名：{}", docSaveDto.getDocName());
            throw new BizException(BizErrorCode.SYSTEM_ERROR);
        } catch (Exception e) {
            logger.error("文件上传发生未知错误,", e);
            throw e;
        }
        //2.保存文件到db
        Doc doc = new Doc();
        doc.setDocId(docId);
        doc.setDocName(docSaveDto.getDocName());
        docRepository.saveDoc(doc);
        return docId;
    }

    @Override
    public List<DocVO> searchDoc(DocSearchDTO searchDto) {
        SearchHit[] searchHits = esDocumentManager.searchByField(EsIndexEnum.DOC_SEARCH.getCode(), "docContents", searchDto.getKeyword());
        if (searchHits == null || searchHits.length == 0) {
            return new ArrayList<>();
        }

        List<String> docIds = Arrays.stream(searchHits)
                .map(SearchHit::getSourceAsString)
                .map(str -> JSONObject.parseObject(str).getString("docId"))
                .collect(Collectors.toList());
        List<Doc> docs = docRepository.queryByDocIds(docIds);
        return docs.stream().map(doc -> {
            DocVO vo = new DocVO();
            BeanUtils.copyProperties(doc, vo);
            return vo;
        }).collect(Collectors.toList());


    }

    @Autowired
    public void setDocRepository(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    @Autowired
    public void setEsDocumentManager(EsDocumentManager esDocumentManager) {
        this.esDocumentManager = esDocumentManager;
    }
}
