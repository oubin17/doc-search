package com.odk.template.api.interfaces;

import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.request.DirectoryCreateRequest;

/**
 * DirectoryApi
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
public interface DirectoryApi {

    /**
     * 创建目录
     *
     * @param directoryCreateRequest
     * @return
     */
     ServiceResponse<String> createDirectory(DirectoryCreateRequest directoryCreateRequest);
}
