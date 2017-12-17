package com.gersion.service;

import com.gersion.model.GirlCategoryInfo;
import com.gersion.model.GirlImageInfo;
import com.gersion.model.GirlType;
import com.gersion.model.ImagePageParam;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImageService {

    PageInfo<GirlImageInfo> getImageListByGirlType(ImagePageParam bean);

    List<GirlType> getAllImageCategory();

    List<GirlImageInfo> getAllImageByCategoryId(int categoryId);

    PageInfo<GirlCategoryInfo> getCategoryListByGirlType(ImagePageParam bean);

    List<GirlCategoryInfo> getCategoryByIds(String[] categoryIds);

    long getMaxCategoryId();

    long getMinCategoryId();
}
