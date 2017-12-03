package com.gaofen.common.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuperLogger {
    /**
     * 获取最原始被调用的堆栈信息
     *
     * @author yzChen
     * @date 2016年10月13日 下午11:50:59
     */
    public static StackTraceElement findCaller() {
        // 获取堆栈信息
        StackTraceElement[] callStack = Thread.currentThread().getStackTrace();

        if (null == callStack) {
            return null;
        }

        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;
        // 日志类名称
        String logClassName = SuperLogger.class.getName();
        // 循环遍历到日志类标识
        boolean isEachLogClass = false;

        // 遍历堆栈信息，获取出最原始被调用的方法信息
        for (StackTraceElement s : callStack) {
            // 遍历到日志类
            if (logClassName.equals(s.getClassName())) {
                isEachLogClass = true;
            }
            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogClass) {
                if (!logClassName.equals(s.getClassName())) {
                    isEachLogClass = false;
                    caller = s;
                    break;
                }
            }
        }

        return caller;
    }

    /**
     * 自动匹配请求类名，生成logger对象，此处 logger name 值为 [className].[methodName]() Line: [fileLine]
     *
     * @author yzChen
     * @date 2016年10月13日 下午11:50:59
     */
    public static Logger logger() {
        // 最原始被调用的堆栈对象
        StackTraceElement caller = findCaller();
        if (null == caller) {
            return LoggerFactory.getLogger(SuperLogger.class);
        }

        Logger log = LoggerFactory.getLogger(caller.getClassName() + "." + caller.getMethodName() + "() Line: " + caller.getLineNumber());
        return log;
    }


    public static void trace(String msg) {
        trace(msg, null);
    }

    public static void trace(String msg, Throwable e) {
        logger().trace(msg, e);
    }

    public static void d(Object msg) {
        d(msg, null);
    }

    public static void d(Object msg, Throwable e) {
        logger().debug(msg == null ? "NULL" : msg.toString(), e);
    }

    public static void i(String msg) {
        i(msg, null);
    }

    public static void i(String msg, Throwable e) {
        logger().info(msg, e);
    }

    public static void warn(String msg) {
        warn(msg, null);
    }

    public static void warn(String msg, Throwable e) {
        logger().warn(msg, e);
    }

    public static void e(Object msg) {
        e(msg, null);
    }

    public static void e(Object msg, Throwable e) {
        logger().error(msg == null ? "NULL" : msg.toString(), e);
    }
}
