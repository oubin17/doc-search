package com.odk.odktemplatemanager.impl;

import com.alibaba.fastjson.JSONObject;
import com.odk.odktemplatemanager.EsDocumentManager;
import com.odk.template.util.constext.ServiceContextHolder;
import com.odk.template.util.dto.DocSearchDTO;
import com.odk.template.util.enums.EsIndexEnum;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * EsDocumentManagerImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Service
public class EsDocumentManagerImpl implements EsDocumentManager {

    private static final Logger logger = LoggerFactory.getLogger(EsDocumentManagerImpl.class);

    private RestHighLevelClient restHighLevelClient;

    @Override
    public void writeEs(String index, Map<String, Object> contents) {
        IndexRequest indexRequest = new IndexRequest(EsIndexEnum.DOC_SEARCH.getCode()).source(JSONObject.toJSONString(contents), XContentType.JSON);
        BulkRequest request = new BulkRequest();
        request.add(indexRequest);
        try {
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            logger.info("bulkResponse result: {}", bulkResponse.hasFailures());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByDocId(String docId) {
        String esIdByContentId = getEsIdByContentId(docId);
        if (StringUtils.isEmpty(esIdByContentId)) {
            return;
        }
        DeleteRequest deleteRequest = new DeleteRequest(EsIndexEnum.DOC_SEARCH.getCode(), esIdByContentId);
        DeleteResponse delete = null;
        try {
            delete = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SearchResponse searchDoc(String index, DocSearchDTO searchDto) {
        SearchRequest request = new SearchRequest(EsIndexEnum.DOC_SEARCH.getCode());
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(matchQuery("userId", ServiceContextHolder.getUserId()));

        BoolQueryBuilder keywordBuilder = QueryBuilders.boolQuery();
        keywordBuilder.should(matchQuery("docContents", searchDto.getKeyword()));
        boolQueryBuilder.must(keywordBuilder);
        sourceBuilder.query(boolQueryBuilder);

        //排序分页
//        List<FieldSortBuilder> fieldSortList = new ArrayList<>();
//        fieldSortList.add(SortBuilders.fieldSort("create_time").order(SortOrder.DESC));
        sourceBuilder.sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        sourceBuilder.from(searchDto.getPageNo() * searchDto.getPageSize());
        sourceBuilder.size(searchDto.getPageSize());

        sourceBuilder.fetchSource(new String[]{"id", "docId", "docName", "createTime"}, new String[]{"docContents"});

        request.source(sourceBuilder);
        SearchResponse response = null;
        try {
            return response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getEsIdByContentId(String docId) {
        String id = "";
        SearchRequest request = new SearchRequest(EsIndexEnum.DOC_SEARCH.getCode());
        //构建查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.should(matchQuery("docId", docId));
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


    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
}
