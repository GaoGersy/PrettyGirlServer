package com.gaofen.common.executor;

import com.gaofen.common.constant.Constants;
import com.gaofen.common.utils.SuperLogger;
import com.gaofen.mapper.GirlCategoryInfoMapper;
import com.gaofen.mapper.GirlImageInfoMapper;
import com.gaofen.mapper.GirlTypeMapper;
import com.gaofen.model.GirlCategoryInfo;
import com.gaofen.model.GirlImageInfo;
import com.gaofen.model.GirlType;

import org.apache.commons.lang.text.StrBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class ImageStorage {

    @Autowired
    GirlImageInfoMapper mGirlImageInfoMapper;

    @Autowired
    GirlTypeMapper mGirlTypeMapper;

    @Autowired
    GirlCategoryInfoMapper mGirlCategoryInfoMapper;

    private static final String SOURCE_PATH = "D:\\xht66\\";
    private static final String TARGET_PATH = "D:\\prettyGirl\\image\\";
    private ThreadPoolExecutor mExecutor;
    private List<String> folders = new ArrayList<>();
    private Integer mCategoryId;

    public void start() {
        scanFolder(SOURCE_PATH);
    }

    /**
     * 扫描目录
     */
    private void scanFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
//                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        scanFolder(file2.getAbsolutePath());
                    } else {
                        handleFile(file2);
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        clearFolders(path);
    }

    private void clearFolders(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        file2.delete();
                    } else {
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    private void handleFile(File file) {
        String folderName = getFolderName(file);
        String fileName = file.getName();
        int index = folders.indexOf(folderName);
        if (index == -1) {
            folders.add(folderName);
            index = folders.size() - 1;
        }
        String targetFolderName = getTargetFolderName(index, 0);
        String targetFolderPath = TARGET_PATH + targetFolderName;
        File targetFolder = new File(targetFolderPath);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        String pathname = targetFolderPath + "\\" + fileName;
        boolean success = file.renameTo(new File(pathname));
        if (success) {
            saveCategoryInfo2DB(folderName, fileName, targetFolderName);

            saveGirlImageInfo2DB(folderName, fileName, targetFolderName);

            saveGirlType2DB();
        }else {
            SuperLogger.e("转存文件失败");
        }

    }

    private void saveGirlType2DB() {
        String categoryName = getCategoryName();

        List<GirlType> imageByCategoryName = mGirlTypeMapper.getCategoryByName(categoryName);
        if (imageByCategoryName.size() == 0) {
            GirlType girlType = new GirlType();
            girlType.setCategoryName(categoryName);
            girlType.setGirlType(1);
            mGirlTypeMapper.insert(girlType);
        }
    }

    private void saveGirlImageInfo2DB(String folderName, String fileName, String targetFolderName) {
        GirlImageInfo girlImageInfo = new GirlImageInfo();
        girlImageInfo.setImageUrl(targetFolderName + "/" + fileName);
        girlImageInfo.setTitle(folderName);
        girlImageInfo.setGirlType(Constants.ImageType.PURE_BEAUTY_GIRL);
        girlImageInfo.setCategoryId(mCategoryId);
        mGirlImageInfoMapper.insert(girlImageInfo);
    }

    private void saveCategoryInfo2DB(String folderName, String fileName, String targetFolderName) {
        List<GirlCategoryInfo> categoryListByTitle = mGirlCategoryInfoMapper.getCategoryByTitle(folderName);
        if (categoryListByTitle.size() == 0) {
            GirlCategoryInfo girlCategoryInfo = new GirlCategoryInfo();
            girlCategoryInfo.setGirlType(1);
            girlCategoryInfo.setDescription("还没有任何描述信息");
            girlCategoryInfo.setTitle(folderName);
            girlCategoryInfo.setIcon(targetFolderName + "/" + fileName);
            girlCategoryInfo.setPraiseNumber(0);
            girlCategoryInfo.setViewNumber(0);
            mGirlCategoryInfoMapper.insert(girlCategoryInfo);
            mCategoryId = girlCategoryInfo.getCategoryId();
        }
    }

    private String getCategoryName() {
        return "清纯美女";
    }

    private String getTargetFolderName(int index, int count) {
        int i = index / 10;
        count++;
        System.out.println(i);
        StrBuilder sb = new StrBuilder();
        if (i > 10) {
            getTargetFolderName(i, count);
        } else {
            int j = 6 - count;
            int multi = 1;
            for (int n = 0; n < j; n++) {
                multi *= 10;
            }
            sb.append(multi).append(index);
        }
        String s = sb.toString();
        return s;
    }

    private String getFolderName(File file) {
        String parent = file.getParent();
        int i = parent.lastIndexOf("\\") + 1;
        return parent.substring(i);
    }

}
