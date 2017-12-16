package com.gersion.common.executor;

import com.gersion.common.utils.SuperLogger;
import com.gersion.mapper.FolderInfoMapper;
import com.gersion.mapper.GirlCategoryInfoMapper;
import com.gersion.mapper.GirlImageInfoMapper;
import com.gersion.mapper.GirlTypeMapper;
import com.gersion.model.FolderInfo;
import com.gersion.model.GirlCategoryInfo;
import com.gersion.model.GirlImageInfo;
import com.gersion.model.GirlType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ImageStorage {
    private static final String[] GIRL_TYPES = {
            "美媛馆",
            "清纯美女",
            "V女郎",
            "假面女皇",
            "花花公子女郎",
            "推女神",
            "推女郎",
            "尤果网",
            "爱丝AISS",
            "秀人",
            "高叉舍宾亮丝",
            "韩国",
            "国模",
            "极品",
            "精品美模",
            "动态图",
            "无圣光",
            "无忌影社"
    };

    @Autowired
    GirlImageInfoMapper mGirlImageInfoMapper;

    @Autowired
    GirlTypeMapper mGirlTypeMapper;

    @Autowired
    GirlCategoryInfoMapper mGirlCategoryInfoMapper;

    @Autowired
    FolderInfoMapper mFolderInfoMapper;

    @Value("#{configProperties['imageSourePath']}")
    private String SOURCE_PATH;

    @Value("#{configProperties['imageTargetPath']}")
    private String TARGET_PATH;

    private List<String> notRemoveFileNames = new ArrayList<>();//没有转存的文件列表

    private List<String> folders = new ArrayList<>();
    private Integer mCategoryId;
    private int startFolderNum = 10000000;
    private static final int OTHER_GIRL = 66;
    private long mStartTime;
    private ThreadPoolExecutor mExecutorPool;

    public void start() {
        mStartTime = System.currentTimeMillis();
        getLastFolderName();
        mExecutorPool = createExecutorPool();
        scanFolder(SOURCE_PATH);
        onWorkEnd(SOURCE_PATH);
    }

    private void getLastFolderName() {
        String lastFolderName = mFolderInfoMapper.getLastFolderName();
        if (lastFolderName != null) {
            startFolderNum = Integer.parseInt(lastFolderName);
        }
    }

    /**
     * 扫描目录
     */
    private void scanFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        scanFolder(file2.getAbsolutePath());
                    } else {
                        handleFile(file2);
//                        mExecutorPool.submit(new StorageWork(file2));
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

    private void onWorkEnd(String path) {
        int size = notRemoveFileNames.size();
        if (size==0){
            clearFolders(path);
            SuperLogger.e("文件全部入库成功，已经清除原有目录...");
        }else {
            SuperLogger.e(size +"个文件重复，没有转存,检查下吧！");
            SuperLogger.e("未转存的文件："+notRemoveFileNames);
        }
        long endTime = System.currentTimeMillis();
        long time = endTime - mStartTime;

        SuperLogger.e("花费了：" + (time / 1000) + " s");
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
                        boolean delete = file2.delete();
                        if (!delete) {
                            SuperLogger.e("文件夹删除失败了....");
                        }
                    } else {
                    }
                }
            }
        } else {
            SuperLogger.e("文件夹不存在!");
        }
    }

    private ThreadPoolExecutor createExecutorPool() {
        ThreadPoolExecutor poolExecutor =
                new ThreadPoolExecutor(3, 8, 0, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());
        return poolExecutor;
    }

    class StorageWork implements Callable<String> {

        private File mFile;

        private StorageWork(File file) {
            mFile = file;
        }

        @Override
        public String call() throws Exception {
            handleWork();
//            String log="";
//            writeLog(log);
            return "aa";
        }

        /**
         * 将文件剪切到指定位置，写入数据库信息
         */
        private void handleWork() {
            handleFile(mFile);
        }

        /**
         * 写入处理结果的日志，为了中断后续传
         */
        private void writeLog(String log) {

        }
    }

    private void handleFile(File file) {
        String folderName = getFolderName(file);
        String fileName = file.getName();
        if (isFileStored(folderName,fileName)) {
            notRemoveFileNames.add(fileName);
            SuperLogger.e("该图片已经入库了，请勿重复录入...");
            return;
        }
        int index = folders.indexOf(folderName);
        if (index == -1) {
            folders.add(folderName);
            index = folders.size() - 1;
        }
        String targetFolderName = getTargetFolderName(index, folderName);
        String targetFolderPath = TARGET_PATH + targetFolderName;
        File targetFolder = new File(targetFolderPath);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }
        String pathname = targetFolderPath + "\\" + fileName;
        saveWeiYiData(file, folderName, fileName, targetFolderName, pathname);
    }

    /**
     * 入库唯一图片站的图片
     */
    @Transactional
    public void saveWeiYiData(File file, String folderName, String fileName, String targetFolderName, String pathname) {
        String absolutePath = file.getAbsolutePath();
        String substring = absolutePath.replace(SOURCE_PATH, "");
        String categoryName = substring.substring(0, substring.indexOf("\\"));
        int type = saveGirlType2DB(categoryName, folderName);
        saveCategoryInfo2DB(type, folderName, fileName, targetFolderName);
        saveGirlImageInfo2DB(type, folderName, fileName, targetFolderName);
        saveFolderName2DB(targetFolderName);
        boolean success = file.renameTo(new File(pathname));
        if (!success) {
            throw new RuntimeException("转存文件失败");
        }
    }

    private int saveGirlType2DB(String categoryName, String folderName) {
        int type = 1;
        Integer result = mGirlTypeMapper.getCategoryByName(categoryName);
        if (result == null) {
            Integer lastType = mGirlTypeMapper.getLastType();
            type = (lastType==null?0:lastType) + 1;
            GirlType girlType = new GirlType();
            girlType.setCategoryName(categoryName);
            girlType.setGirlType(type);
            int insert = mGirlTypeMapper.insert(girlType);
            if (insert <= 0) {
                throw new RuntimeException("girl_type 插入数据失败");
            }

        } else {
            type = result;
        }
        return type;
    }

    @Transactional
    public void saveXHTData(File file, String folderName, String fileName, String targetFolderName, String pathname) {
        int type = -1;
        String categoryName = null;
        for (int i = 0; i < GIRL_TYPES.length; i++) {
            String name = GIRL_TYPES[i];
            if (folderName.contains(name)) {
                type = i;
                categoryName = name;
                break;
            }
        }
        if (type == -1) {
            type = OTHER_GIRL;
            categoryName = "精品美女";
        }
        saveCategoryInfo2DB(type, folderName, fileName, targetFolderName);
        saveGirlImageInfo2DB(type, folderName, fileName, targetFolderName);
        saveGirlType2DB(type, categoryName, folderName);
        saveFolderName2DB(targetFolderName);
        boolean success = file.renameTo(new File(pathname));
        if (!success) {
            throw new RuntimeException("转存文件失败");
        }
    }

    private void saveFolderName2DB(String targetFolderName) {
        FolderInfo folderInfo = mFolderInfoMapper.getFolderInfo(targetFolderName);
        if (folderInfo == null) {
            FolderInfo record = new FolderInfo();
            record.setFolderName(targetFolderName);
            int insert = mFolderInfoMapper.insert(record);
            if (insert <= 0) {
                throw new RuntimeException("folder_info 插入数据失败");
            }
        }
    }

    private boolean isFileStored(String folderName,String fileName) {
        String name = "%/" + fileName;
        GirlImageInfo info = new GirlImageInfo();
        info.setTitle(folderName);
        info.setImageUrl(name);
        String imageByTitle = mGirlImageInfoMapper.getImageByUrl(info);
        if (imageByTitle != null) {
            return true;
        }
        return false;
    }

    private int saveGirlType2DB(int type, String categoryName, String folderName) {
        Integer result = mGirlTypeMapper.getCategoryByName(categoryName);
        if (result == null) {
            GirlType girlType = new GirlType();
            girlType.setCategoryName(categoryName);
            girlType.setGirlType(type);
            int insert = mGirlTypeMapper.insert(girlType);
            if (insert <= 0) {
                throw new RuntimeException("girl_type 插入数据失败");
            }
        }
        return type;
    }

    private void saveGirlImageInfo2DB(int type, String folderName, String fileName, String targetFolderName) {
        GirlImageInfo girlImageInfo = new GirlImageInfo();
        girlImageInfo.setImageUrl(targetFolderName + "/" + fileName);
        girlImageInfo.setTitle(folderName);
        girlImageInfo.setGirlType(type);
        girlImageInfo.setCategoryId(mCategoryId);
        int insert = mGirlImageInfoMapper.insert(girlImageInfo);
        if (insert <= 0) {
            throw new RuntimeException("girl_image_info 插入数据失败");
        }
    }

    private void saveCategoryInfo2DB(int type, String folderName, String fileName, String targetFolderName) {
        List<GirlCategoryInfo> categoryListByTitle = mGirlCategoryInfoMapper.getCategoryByTitle(folderName);
        if (categoryListByTitle.size() == 0) {
            GirlCategoryInfo girlCategoryInfo = new GirlCategoryInfo();
            girlCategoryInfo.setGirlType(type);
            girlCategoryInfo.setDescription("还没有任何描述信息");
            girlCategoryInfo.setTitle(folderName);
            girlCategoryInfo.setIcon(targetFolderName + "/" + fileName);
            girlCategoryInfo.setPraiseNumber(0);
            girlCategoryInfo.setViewNumber(0);
            girlCategoryInfo.setFolderName(targetFolderName);
            int insert = mGirlCategoryInfoMapper.insert(girlCategoryInfo);
            mCategoryId = girlCategoryInfo.getCategoryId();
            if (insert <= 0) {
                throw new RuntimeException("category_info 插入数据失败");
            }
        } else {
            mCategoryId = categoryListByTitle.get(0).getCategoryId();
        }
    }

    private String getTargetFolderName(int index, String title) {
        String folderName = mGirlCategoryInfoMapper.getFolderNameByTitle(title);
        if (folderName == null) {
            int folderNum = startFolderNum + index + 1;
            folderName = folderNum + "";
        }
        return folderName;
    }

    private String getFolderName(File file) {
        String parent = file.getParent();
        int i = parent.lastIndexOf("\\") + 1;
        return parent.substring(i);
    }

}
