package com.gaofen.mapper;

import com.gaofen.model.GirlCategoryInfo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GirlCategoryInfoMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(GirlCategoryInfo record);

    int insertSelective(GirlCategoryInfo record);

    GirlCategoryInfo selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(GirlCategoryInfo record);

    int updateByPrimaryKey(GirlCategoryInfo record);

    List<GirlCategoryInfo> getCategoryByTitle(@Param("title") String title);

    List<GirlCategoryInfo> getCategoryByType(@Param("girlType")Integer bean);
}