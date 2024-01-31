package com.odk.odktemplateservice.impl;

import com.alibaba.fastjson.JSONObject;
import com.odk.base.exception.AssertUtil;
import com.odk.base.exception.BizErrorCode;
import com.odk.base.exception.BizException;
import com.odk.base.util.LocalDateTimeUtil;
import com.odk.base.vo.response.PageResponse;
import com.odk.odktemplatemanager.EsDocumentManager;
import com.odk.odktemplatemanager.util.FileUtil;
import com.odk.odktemplateservice.DocService;
import com.odk.template.domain.domain.Doc;
import com.odk.template.domain.impl.DirectoryRepository;
import com.odk.template.domain.impl.DocRepository;
import com.odk.template.util.constext.ServiceContextHolder;
import com.odk.template.util.dto.DocSaveDTO;
import com.odk.template.util.dto.DocSearchDTO;
import com.odk.template.util.enums.EsIndexEnum;
import com.odk.template.util.vo.DocVO;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZoneOffset;
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

    private DirectoryRepository directoryRepository;

    private EsDocumentManager esDocumentManager;


    @Override
    public String deleteDoc(String docId) {
        //1.检查是否是你本人的文件
        Doc doc = docRepository.queryDoc(docId, ServiceContextHolder.getUserId());
        AssertUtil.notNull(doc, BizErrorCode.PARAM_ILLEGAL, "文件不存在");
        //2.删除es文件
        esDocumentManager.deleteByDocId(docId);
        //3.删除本地文件
        FileUtil.deleteFile(doc.getDocPath());

        return null;
    }

    @Override
    public String saveDoc(DocSaveDTO docSaveDto) {
        //校验上传路径是否合法
        boolean existence = directoryRepository.checkExistence(docSaveDto.getDirId(), ServiceContextHolder.getUserId());
        AssertUtil.isTrue(existence, BizErrorCode.PARAM_ILLEGAL, "文件夹不存在");

        String docId = UUID.randomUUID().toString();
        String finalPath;

        try {
            //1.上传文件
            finalPath = FileUtil.saveFile(filePath, docId, docSaveDto.getDocName(), docSaveDto.getFileInputStream());
            //2.获取文件内容
            String docContents = FileUtil.getDocContents(filePath, docId, docSaveDto.getDocName());
            logger.info("文件内容 {}", docContents);
            if (StringUtils.isNotEmpty(docContents)) {
                //3.内容写到ES
                Map<String, Object> content = new HashMap<>();
                content.put("userId", ServiceContextHolder.getUserId());
                content.put("docId", docId);
                content.put("docName", docSaveDto.getDocName());
                content.put("docContents", docContents);

                content.put("createTime", LocalDateTimeUtil.getCurrentDateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
                content.put("updateTime", LocalDateTimeUtil.getCurrentDateTime().toInstant(ZoneOffset.UTC).toEpochMilli());
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
        doc.setDirId(docSaveDto.getDirId());
        doc.setUserId(ServiceContextHolder.getUserId());
        doc.setDocPath(finalPath);
        docRepository.saveDoc(doc);
        return docId;
    }

    @Override
    public PageResponse<DocVO> searchDoc(DocSearchDTO searchDto) {
        SearchResponse searchResponse = esDocumentManager.searchDoc(EsIndexEnum.DOC_SEARCH.getCode(), searchDto);
        if (null == searchResponse || searchResponse.getHits().getHits().length == 0) {
            return PageResponse.ofEmpty();
        }
        int hitCount = (int) searchResponse.getHits().getTotalHits().value;
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        List<String> docIds = Arrays.stream(searchHits)
                .map(SearchHit::getSourceAsString)
                .map(str -> JSONObject.parseObject(str).getString("docId"))
                .collect(Collectors.toList());
        List<Doc> docs = docRepository.queryByDocIds(docIds);
        List<DocVO> collect = docs.stream().map(doc -> {
            DocVO vo = new DocVO();
            BeanUtils.copyProperties(doc, vo);
            return vo;
        }).collect(Collectors.toList());
        return PageResponse.of(collect, hitCount);


    }

    @Autowired
    public void setDocRepository(DocRepository docRepository) {
        this.docRepository = docRepository;
    }

    @Autowired
    public void setEsDocumentManager(EsDocumentManager esDocumentManager) {
        this.esDocumentManager = esDocumentManager;
    }

    @Autowired
    public void setDirectoryRepository(DirectoryRepository directoryRepository) {
        this.directoryRepository = directoryRepository;
    }
}
