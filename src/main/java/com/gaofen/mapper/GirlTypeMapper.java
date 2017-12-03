package com.gaofen.mapper;

import com.gaofen.model.GirlType;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GirlTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GirlType record);

    int insertSelective(GirlType record);

    GirlType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GirlType record);

    int updateByPrimaryKey(GirlType record);

    List<GirlType> getCategoryByName(@Param("categoryName") String categoryName);

    List<GirlType> getAllImageCategory();
}