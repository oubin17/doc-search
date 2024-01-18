package com.odk.odktemplatemanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.odk.odktemplatemanager.EsDocumentManager;
import com.odk.odktemplatemanager.EsManager;
import com.odk.odktemplatemanager.config.EsFunUtil;
import com.odk.template.util.enums.EsIndexEnum;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;


/**
 * EsManagerImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/16
 */
@Service
public class EsManagerImpl implements EsManager {

    private static final Logger logger = LoggerFactory.getLogger(EsManagerImpl.class);

    private RestHighLevelClient restHighLevelClient;

    private EsDocumentManager esDocumentManager;

    @Override
    public void writeToEs(String docId, String docName, String docContents) {

        createIndex(EsIndexEnum.DOC_SEARCH.getCode());
        writeData(docId, docName, docContents);
        System.out.println("搜索结果：" + getEsByContent("回调"));
        System.out.println("搜索结果：" + getEsByContent("无法123"));

        delete(docId);
    }



    public void writeData(String docId, String docName, String docContents) {
        Map<String, String> content = new HashMap<>();
        content.put("docId", docId);
        content.put("docName", docName);
        content.put("docContents", docContents);
        IndexRequest indexRequest = new IndexRequest(EsIndexEnum.DOC_SEARCH.getCode()).source(JSONObject.toJSONString(content), XContentType.JSON);
        BulkRequest request = new BulkRequest();
        request.add(indexRequest);
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("bulkResponse result: {}", bulkResponse.hasFailures());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 通过实体类id，找到es id，再通过es id删除
     *
     * @param id
     */
    public void delete(String id) {
        String esIdbyContentId = getEsIdbyContentId(id);
        DeleteRequest deleteRequest = new DeleteRequest(EsIndexEnum.DOC_SEARCH.getCode(), esIdbyContentId);
        DeleteResponse delete = null;
        try {
            delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DocWriteResponse.Result result = delete.getResult();
//        String s = checkResponse(result);
//        logger.info(s);
    }

    public String getEsByContent(String content) {
        String id = "";
        SearchRequest request = new SearchRequest(EsIndexEnum.DOC_SEARCH.getCode());
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQuery("docContents", content));
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long value = response.getHits().getTotalHits().value;
        SearchHit[] hits = response.getHits().getHits();

        for (SearchHit hit : hits) {
            id = hit.getId();
            id = JSONObject.parseObject(hit.getSourceAsString()).getString("docId");

        }
        return id;
    }

    public String getEsIdbyContentId(String contentId) {
        String id = "";
        SearchRequest request = new SearchRequest(EsIndexEnum.DOC_SEARCH.getCode());
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQuery("docId", contentId));
        sourceBuilder.query(boolQueryBuilder);
        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long value = response.getHits().getTotalHits().value;
        SearchHit[] hits = response.getHits().getHits();

        for (SearchHit hit : hits) {
            id = hit.getId();
        }
        return id;
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     * @throws IOException
     */
    public boolean existsIndex(String index) {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        logger.info("source:" + request);
        boolean exists = false;
        try {
            exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("existsIndex: " + exists);
        return exists;
    }

    /**
     * 创建索引
     *
     * @param indexName
     */
    public void createIndex(String indexName) {
        try {
            CreateIndexRequest createIndexRequest = EsFunUtil.createDocIndex(indexName);
            if (!existsIndex(indexName)) {
                CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                System.out.println(response.toString());
                logger.info("索引创建结查：" + response.isAcknowledged());
            } else {
                logger.warn("索引已经存在，请勿重复创建");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @Autowired
    public void setEsDocumentManager(EsDocumentManager esDocumentManager) {
        this.esDocumentManager = esDocumentManager;
    }
}
