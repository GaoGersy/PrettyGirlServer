package com.gersion.controller;

import com.gersion.common.dto.Result;
import com.gersion.common.utils.GsonQuick;
import com.gersion.common.utils.SuperLogger;
import com.gersion.model.GirlCategoryInfo;
import com.gersion.model.GirlImageInfo;
import com.gersion.model.GirlType;
import com.gersion.model.ImagePageParam;
import com.gersion.service.ImageService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "/image")
public class ImageController {

    @Autowired
    ImageService mImageService;

    @PostMapping(value = "/getImageListByGirlType")
    public Result getImageListByImageType(@RequestBody ImagePageParam bean) {
        SuperLogger.e(bean);
        Result result = Result.getInstance();
        try {
            PageInfo<GirlImageInfo> imageList = mImageService.getImageListByGirlType(bean);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @PostMapping(value = "/getCategoryListByGirlType")
    public Result getCategoryListByGirlType(@RequestBody ImagePageParam bean) {
        Result result = Result.getInstance();
        try {
            PageInfo<GirlCategoryInfo> imageList = mImageService.getCategoryListByGirlType(bean);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @GetMapping(value = "/getImageListByCategoryId")
    public Result getImageListByCategoryId(@RequestParam(value="categoryId") int categoryId) {
        Result result = Result.getInstance();
        try {
            List<GirlImageInfo> imageList = mImageService.getAllImageByCategoryId(categoryId);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @GetMapping(value = "/getAllImageCategory")
    public Result getAllImageCategory() {
        Result result = Result.getInstance();
        try {
            List<GirlType> imageList = mImageService.getAllImageCategory();
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @PostMapping(value = "/getCategoryByIds")
    public Result getCategoryByIds(@RequestBody String paramString) {
        Result result = Result.getInstance();
        SuperLogger.e(paramString);
        try {
            String categoryIds = GsonQuick.getString(paramString,"categoryIds");
            String[] ids = categoryIds.split(",");
            List<GirlCategoryInfo> imageList = mImageService.getCategoryByIds(ids);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @GetMapping(value = "/getMaxCategoryId")
    public Result getMaxCategoryId() {
        Result result = Result.getInstance();
        try {
            long imageList = mImageService.getMaxCategoryId();
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @GetMapping(value = "/getMinCategoryId")
    public Result getMinCategoryId() {
        Result result = Result.getInstance();
        try {
            long imageList = mImageService.getMinCategoryId();
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }
}
