package com.gersion.common.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class StorageExecutor {

    private ScheduledThreadPoolExecutor mExecutor;
    private ScheduledWork mScheduledWork;

    public void start() {
        mExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3);
        mScheduledWork = new ScheduledWork();
        mExecutor.scheduleAtFixedRate(mScheduledWork, 0, 2, TimeUnit.SECONDS);
    }

    public void shutdown(){
        mExecutor.shutdown();
        mScheduledWork.shutdown();
    }

    public void shutdownNow(){
        mExecutor.shutdownNow();
        mScheduledWork.shutdownNow();
    }

    static class ScheduledWork implements Runnable {

        private final List<ExecutorService> mExecutors;

        private ScheduledWork() {
            mExecutors = new ArrayList<>();
            System.out.println("任务初始化ing...");
        }

        @Override
        public void run() {
            int taskCount = 4;
            initExecutors(taskCount);
            List<List<String>> tasks = new ArrayList<>();
            int poolSize = tasks.size();

            for (int i = 0; i < taskCount; i++) {
                List<String> task = new ArrayList<>();
                tasks.add(task);
            }
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < poolSize; j++) {
                    List<String> task = tasks.get(j);
                    task.add("线程" + j + "---" + (i + 10 * j));
                }
            }
            taskCount=getConfigureFromDB();
            scanStorageFile(tasks);
            submitAllPool(tasks, poolSize);
        }

        /**
         * 扫描等待处理的文件
         * @param tasks
         */
        private void scanStorageFile(List<List<String>> tasks) {
            System.out.println("扫描文件成功...");
        }

        /**
         * 从数据库读取jar包与卫星的对应关系
         * @return
         */
        private int getConfigureFromDB() {
            System.out.println("获取配置信息成功...");
            return 0;
        }

        public void shutdown(){
            for (ExecutorService executor : mExecutors) {
                executor.shutdown();
            }
        }

        public void shutdownNow(){
            for (ExecutorService executor : mExecutors) {
                executor.shutdownNow();
            }
        }

        /**
         * 将所有的任务分类放到对应的singlepool中
         * @param tasks
         * @param poolSize
         */
        private void submitAllPool(List<List<String>> tasks, int poolSize) {
            for (int i = 0; i < poolSize; i++) {
                List<String> task = tasks.get(i);
                ExecutorService executorService = mExecutors.get(i);
                addWork2Pool(executorService, task);
            }
        }

        /**
         * 将单个卫星的所有待处理的文件任务加入到singlepool中
         * @param executorService
         * @param task
         */
        private void addWork2Pool(ExecutorService executorService, List<String> task) {
            int size = task.size();
            for (int j = 0; j < size; j++) {
                String s = task.get(j);
                StorageWork work = new StorageWork(s);
                executorService.submit(work);
            }
        }

        /**
         * 如果singlepool的数量少于卫星的数量就添加剩下的singlepool
         * @param taskCount
         */
        private void initExecutors(int taskCount) {
            int noAddedSize = taskCount - mExecutors.size();
            for (int i = 0; i < noAddedSize; i++) {
                ExecutorService workService = Executors.newSingleThreadExecutor();
                mExecutors.add(workService);
            }
        }
    }

    static class StorageWork implements Callable<String> {

        private String mName;

        private StorageWork(String name) {
            mName = name;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(new Random().nextInt(4) * 1000);
            System.out.println("线程执行aaaa" + mName);
            handleWork();
            String log="";
            writeLog(log);
            return mName;
        }

        /**
         * 将文件剪切到指定位置，写入数据库信息
         */
        private void handleWork() {

        }

        /**
         * 写入处理结果的日志，为了中断后续传
         * @param log
         */
        private void writeLog(String log) {

        }
    }
}
