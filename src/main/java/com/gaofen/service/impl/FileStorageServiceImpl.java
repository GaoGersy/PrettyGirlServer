package com.gaofen.service.impl;

import com.gaofen.common.executor.ImageStorage;
import com.gaofen.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    ImageStorage mImageStorage;

    @Override
    public void imageStorage() {
        mImageStorage.start();
    }

    @Override
    public void dataStorage() {

    }

//    public static void get() {
//        ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(3);
//        ExecutorService executorService = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
//        ExecutorService workService = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
//        executor.scheduleAtFixedRate(new ScheduledWork(executorService),0,200, TimeUnit.SECONDS);
////        executor.execute(new ScheduledWork(executorService));
//    }
//
//    static class ScheduledWork implements Runnable {
//
//        private ExecutorService mService;
//        private Collection<StorageWork> mTasks;
//        private final ExecutorService mWorkService1;
//        private final ExecutorService mWorkService2;
//        private final ExecutorService mWorkService3;
//        private final Random mRandom;
//
//        private ScheduledWork(ExecutorService service) {
//            mService = service;
////            mTasks = new ArrayList<>();
//            mRandom = new Random();
//            mWorkService1 = Executors.newSingleThreadExecutor();
//            mWorkService2 = Executors.newSingleThreadExecutor();
//            mWorkService3 = Executors.newSingleThreadExecutor();
//
////            mTasks.add(new StorageWork("线程1"));
////            mTasks.add(new StorageWork("线程2"));
////            mTasks.add(new StorageWork("线程3"));
//            System.out.println("线程初始化完成");
//        }
//
//        @Override
//        public void run() {
//            try {
//                ThreadPoolExecutor service = (ThreadPoolExecutor) mService;
//                for (int i=0;i<20;i++){
//                    mWorkService1.submit(new StorageWork("线程1---"+i));
//                    mWorkService2.submit(new StorageWork("线程2---"+(i+20)));
//                    mWorkService3.submit(new StorageWork("线程3---"+(i+40)));
//                }
//
////                for (StorageWork task : mTasks) {
////                    Future<String> submit = service.submit(task);
////                    System.out.println(submit.get()+submit.isDone());
////                }
////                List<Future<String>> futures = service.invokeAll(mTasks);
////                for (Future<String> future : futures) {
////
////                    System.out.println(future.get() + future.isDone());
////                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
////    static class StorageWork implements Runnable {
////
////        private String mName;
////
////        private StorageWork(String name) {
////            mName = name;
////        }
////
////        @Override
////        public void run() {
////            try {
////                Thread.sleep(5000);
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
////            System.out.println("线程执行aaaa" + mName);
////        }
////    }
//
//    static class StorageWork implements Callable<String> {
//
//        private String mName;
//
//        private StorageWork(String name) {
//            mName = name;
//        }
//
//        @Override
//        public String call() throws Exception {
//            Thread.sleep(new Random().nextInt(4)*2000);
//            System.out.println("线程执行aaaa" + mName);
//            return mName;
//        }
//    }
////    static class StorageWork2 implements Callable<String> {
////
////        private String mName;
////
////        private StorageWork2(String name) {
////            mName = name;
////        }
////
////        @Override
////        public String call() throws Exception {
////            Thread.sleep(new Random().nextInt(4)*2000);
////            System.out.println("线程执行aaaa" + mName);
////            return mName;
////        }
////    }
////    static class StorageWork3 implements Callable<String> {
////
////        private String mName;
////
////        private StorageWork3(String name) {
////            mName = name;
////        }
////
////        @Override
////        public String call() throws Exception {
////            Thread.sleep(new Random().nextInt(4)*2000);
////            System.out.println("线程执行aaaa" + mName);
////            return mName;
////        }
////    }

    public static void main(String[] arg) {
//        StorageExecutor executor = new StorageExecutor();
//        executor.start();

        ImageStorage storage = new ImageStorage();
        storage.start();
    }

}
