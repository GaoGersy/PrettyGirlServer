package com.gersion.service.impl;

import com.gersion.mapper.GirlCategoryInfoMapper;
import com.gersion.mapper.GirlImageInfoMapper;
import com.gersion.mapper.GirlTypeMapper;
import com.gersion.model.GirlCategoryInfo;
import com.gersion.model.GirlImageInfo;
import com.gersion.model.GirlType;
import com.gersion.model.ImagePageParam;
import com.gersion.service.ImageService;
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

    @Override
    public List<GirlCategoryInfo> getCategoryByIds(String[] categoryIds) {
        return mGirlCategoryInfoMapper.getCategoryByIds(categoryIds);
    }

    @Override
    public long getMaxCategoryId() {
        return mGirlCategoryInfoMapper.getMaxCategoryId();
    }

    @Override
    public long getMinCategoryId() {
        return mGirlCategoryInfoMapper.getMinCategoryId();
    }
}
