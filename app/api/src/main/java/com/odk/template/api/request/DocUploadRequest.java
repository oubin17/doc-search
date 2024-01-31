package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

/**
 * DocUploadRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DocUploadRequest extends BaseRequest {

    /**
     * 文件名称
     */
    private String dirId;

    private String name;

    /**
     * 文件输入流
     */
    private InputStream fileInputStream;

    private String fileName;

    private String originalFileName;

    private String contentType;

    private String fileSize;

}
