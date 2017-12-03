package com.gaofen.service;

import com.gaofen.model.GirlCategoryInfo;
import com.gaofen.model.GirlImageInfo;
import com.gaofen.model.GirlType;
import com.gaofen.model.ImagePageParam;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ImageService {

    PageInfo<GirlImageInfo> getImageListByGirlType(ImagePageParam bean);

    List<GirlType> getAllImageCategory();

    List<GirlImageInfo> getAllImageByCategoryId(int categoryId);

    PageInfo<GirlCategoryInfo> getCategoryListByGirlType(ImagePageParam bean);
}
