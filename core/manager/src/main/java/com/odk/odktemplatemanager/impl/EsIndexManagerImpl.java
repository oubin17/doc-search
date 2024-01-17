package com.odk.odktemplatemanager.impl;

import com.odk.odktemplatemanager.EsIndexManager;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * EsIndexManagerImpl
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
@Service
public class EsIndexManagerImpl implements EsIndexManager {

    private static final Logger logger = LoggerFactory.getLogger(EsIndexManagerImpl.class);

    private RestHighLevelClient restHighLevelClient;

    @Override
    public boolean rebuildIndex(String index, CreateIndexRequest createIndexRequest) {
        deleteIndex(index);
        createIndex(index, createIndexRequest);
        return true;
    }

    @Override
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

    @Override
    public void createIndex(String index, CreateIndexRequest createIndexRequest) {
        try {
//            CreateIndexRequest createIndexRequest = EsFunUtil.createIndex(index);
            if (!existsIndex(index)) {
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

    @Override
    public void deleteIndex(String index) {
        try {
            if (existsIndex(index)) {
                DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
                logger.info("source:" + deleteIndexRequest);
                restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
}
