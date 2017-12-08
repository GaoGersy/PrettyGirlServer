package com.gersion.controller;

import com.gersion.common.dto.Result;
import com.gersion.common.utils.SuperLogger;
import com.gersion.model.GirlCategoryInfo;
import com.gersion.model.GirlImageInfo;
import com.gersion.model.GirlType;
import com.gersion.model.ImagePageParam;
import com.gersion.service.ImageService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/image")
public class ImageController {

    @Autowired
    ImageService mImageService;

    @ResponseBody
    @RequestMapping(value = "/getImageListByGirlType",method = RequestMethod.POST)
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

    @ResponseBody
    @RequestMapping(value = "/getCategoryListByGirlType",method = RequestMethod.POST)
    public Result getCategoryListByGirlType(@RequestBody ImagePageParam bean) {
        Result result = Result.getInstance();
        try {
            PageInfo<GirlCategoryInfo> imageList = mImageService.getCategoryListByGirlType(bean);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getImageListByCategoryId",method = RequestMethod.GET)
    public Result getImageListByCategoryId(@RequestParam(value="categoryId") int categoryId) {
        Result result = Result.getInstance();
        try {
            List<GirlImageInfo> imageList = mImageService.getAllImageByCategoryId(categoryId);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getAllImageCategory",method = RequestMethod.GET)
    public Result getAllImageCategory() {
        Result result = Result.getInstance();
        try {
            List<GirlType> imageList = mImageService.getAllImageCategory();
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }
}
