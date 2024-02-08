package com.odk.template.web;

import com.odk.base.vo.response.EmptyVO;
import com.odk.base.vo.response.ServiceResponse;
import com.odk.template.api.interfaces.DirectoryApi;
import com.odk.template.api.request.DirectoryCreateRequest;
import com.odk.template.api.request.DirectoryUpdateRequest;
import com.odk.template.util.vo.DirectoryTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping()
    public ServiceResponse<EmptyVO> deleteDirectory(@RequestParam("dirId") String dirId) {
        directoryApi.deleteDirectory(dirId);
        return ServiceResponse.valueOfSuccess();
    }

    @PostMapping("/update")
    public ServiceResponse updateDirName(@RequestBody DirectoryUpdateRequest directoryUpdateRequest) {
        directoryApi.updateDirectory(directoryUpdateRequest);
        return ServiceResponse.valueOfSuccess();
    }

    /**
     * 目录树
     *
     * @return
     */
    @GetMapping("/tree")
    public ServiceResponse<List<DirectoryTreeVO>> directoryTree() {
        return directoryApi.directoryTree();
    }

    @Autowired
    public void setDirectoryApi(DirectoryApi directoryApi) {
        this.directoryApi = directoryApi;
    }
}
