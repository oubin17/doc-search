package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirectoryCreateRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryCreateRequest extends BaseRequest {

    /**
     * 父节点ID
     */
    private String parentId;

    /**
     * 目录名称
     */
    private String name;
}
