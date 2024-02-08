package com.odk.template.api.request;

import com.odk.base.vo.request.BaseRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * DirectoryUpdateRequest
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DirectoryUpdateRequest extends BaseRequest {

    /**
     * 文件夹id
     */
    private String dirId;

    /**
     * 文件夹名称
     */
    private String dirName;
}
