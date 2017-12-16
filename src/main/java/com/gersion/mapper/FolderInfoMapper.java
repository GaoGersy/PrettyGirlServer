package com.gersion.mapper;

import com.gersion.model.FolderInfo;

import org.apache.ibatis.annotations.Param;

public interface FolderInfoMapper {
    int deleteByPrimaryKey(Integer folderId);

    int insert(FolderInfo record);

    int insertSelective(FolderInfo record);

    FolderInfo selectByPrimaryKey(Integer folderId);

    int updateByPrimaryKeySelective(FolderInfo record);

    int updateByPrimaryKey(FolderInfo record);

    FolderInfo getFolderInfo(@Param("folderName")String folderName);

    String getLastFolderName();
}