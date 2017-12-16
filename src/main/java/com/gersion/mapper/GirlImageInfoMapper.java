package com.gersion.mapper;

import com.gersion.model.GirlImageInfo;
import com.gersion.model.ImagePageParam;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GirlImageInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GirlImageInfo record);

    int insertSelective(GirlImageInfo record);

    GirlImageInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GirlImageInfo record);

    int updateByPrimaryKey(GirlImageInfo record);

    List<GirlImageInfo> getImageInfoByType(ImagePageParam bean);

    List<GirlImageInfo> getImageByCategoryId(@Param("categoryId") Integer categoryId);

    String getImageByUrl(GirlImageInfo record);
}