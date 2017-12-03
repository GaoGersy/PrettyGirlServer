package com.gaofen.controller;

import com.gaofen.common.dto.Result;
import com.gaofen.model.GirlCategoryInfo;
import com.gaofen.model.GirlImageInfo;
import com.gaofen.model.GirlType;
import com.gaofen.model.ImagePageParam;
import com.gaofen.service.ImageService;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @RequestMapping(value = "/getImageListByGirlType",method = RequestMethod.GET)
    public Result getImageListByImageType() {
//        SuperLogger.e(bean);
//        @RequestBody
        ImagePageParam bean = new ImagePageParam();
        bean.setGirlType(1);
        bean.setCurrentPage(1);
        bean.setPageSize(30);
        Result result = Result.getInstance();
        try {
            PageInfo<GirlImageInfo> imageList = mImageService.getImageListByGirlType(bean);
            return result.success(imageList);
        } catch (Exception e) {
            return result.exception(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getCategoryListByGirlType",method = RequestMethod.GET)
    public Result getCategoryListByGirlType() {
        ImagePageParam bean = new ImagePageParam();
        bean.setGirlType(1);
        bean.setCurrentPage(1);
        bean.setPageSize(10);
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
