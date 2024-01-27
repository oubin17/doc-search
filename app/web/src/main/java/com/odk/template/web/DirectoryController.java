package com.odk.template.web;

import com.odk.base.vo.response.EmptyVO;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.interfaces.DirectoryApi;
import com.odk.template.api.request.DirectoryCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * DirectoryController
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/24
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {

    private DirectoryApi directoryApi;

    @PostMapping("/create")
    public ServiceResponse<String> createDirectory(@RequestBody DirectoryCreateRequest directoryCreateRequest) {
        return directoryApi.createDirectory(directoryCreateRequest);
    }

    @DeleteMapping("/delete")
    public ServiceResponse<EmptyVO> deleteDirectory(@RequestParam("dir_id") String dirId) {
        directoryApi.deleteDirectory(dirId);
        return ServiceResponse.valueOfSuccess();
    }

    @Autowired
    public void setDirectoryApi(DirectoryApi directoryApi) {
        this.directoryApi = directoryApi;
    }
}
