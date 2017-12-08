package com.gersion.controller;

import com.gersion.common.dto.Result;
import com.gersion.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StorageController {

    @Autowired
    FileStorageService mFileStorageService;

    @RequestMapping(value = "/storageImage")
    @ResponseBody
    public Result storageImage() {
        Result result = Result.getInstance();
        try {
            mFileStorageService.imageStorage();
            return result.success("入档成功");
        } catch (Exception e) {
            return result.exception(e);
        }
    }
}
