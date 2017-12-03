package com.gaofen.service.impl;

import com.gaofen.mapper.GirlCategoryInfoMapper;
import com.gaofen.mapper.GirlImageInfoMapper;
import com.gaofen.mapper.GirlTypeMapper;
import com.gaofen.model.GirlCategoryInfo;
import com.gaofen.model.GirlImageInfo;
import com.gaofen.model.GirlType;
import com.gaofen.model.ImagePageParam;
import com.gaofen.service.ImageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    GirlImageInfoMapper mGirlImageInfoMapper;

    @Autowired
    GirlTypeMapper mGirlTypeMapper;

    @Autowired
    GirlCategoryInfoMapper mGirlCategoryInfoMapper;

    @Override
    public PageInfo<GirlImageInfo> getImageListByGirlType(ImagePageParam bean) {
        PageHelper.startPage(bean.getCurrentPage(), bean.getPageSize());
        List<GirlImageInfo> imageList = mGirlImageInfoMapper.getImageInfoByType(bean);
        PageInfo<GirlImageInfo> pageInfo = new PageInfo<GirlImageInfo>(imageList);
        return pageInfo;
    }

    @Override
    public List<GirlType> getAllImageCategory() {
        return mGirlTypeMapper.getAllImageCategory();
    }

    @Override
    public List<GirlImageInfo> getAllImageByCategoryId(int categoryId) {
        return mGirlImageInfoMapper.getImageByCategoryId(categoryId);
    }

    @Override
    public PageInfo<GirlCategoryInfo> getCategoryListByGirlType(ImagePageParam bean) {
        PageHelper.startPage(bean.getCurrentPage(), bean.getPageSize());
        List<GirlCategoryInfo> imageList = mGirlCategoryInfoMapper.getCategoryByType(bean.getGirlType());
        PageInfo<GirlCategoryInfo> pageInfo = new PageInfo<GirlCategoryInfo>(imageList);
        return pageInfo;
    }
}
