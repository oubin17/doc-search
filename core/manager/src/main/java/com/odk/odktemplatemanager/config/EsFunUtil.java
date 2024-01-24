package com.odk.odktemplatemanager.config;

import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.json.JsonXContent;

import java.io.IOException;

/**
 * EsFunUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/17
 */
public class EsFunUtil {

    /**
     * 创建文件索引
     *
     * @param index
     * @return
     * @throws IOException
     */
    public static CreateIndexRequest createDocIndex(String index) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")

                .startObject("id")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("userId")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("docId")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("docName")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("docContents")
                .field("type", "text")
                .field("index", "true")
                .field("analyzer", "ik_max_word")
                .endObject()

                .startObject("createTime")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()

                .startObject("updateTime")
                .field("type", "keyword")
                .field("index", "true")
                .endObject()


                .endObject()
                .endObject();

        request.mapping(mappingBuilder);
        return request;
    }
}
