package com.gaofen.common.utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OkHttpUtils {

    private static final String TAG = "OkHttpUtils";

    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;

    private OkHttpUtils() {
        /**
         * 构建OkHttpClient
         */
        mOkHttpClient = new OkHttpClient();
        /**
         * 设置连接的超时时间
         */
        mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        /**
         * 设置响应的超时时间
         */
        mOkHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        /**
         * 请求的超时时间
         */
        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        /**
         * 允许使用Cookie
         */
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
    }

    /**
     * 通过单例模式构造对象
     *
     * @return OkHttpUtils
     */
    private synchronized static OkHttpUtils getmInstance() {
        if (mInstance == null) {
            mInstance = new OkHttpUtils();
        }
        return mInstance;
    }


    /**
     * 构造Get请求
     *
     * @param url 请求的url
     * @param callback 结果回调的方法
     */
    private void getRequest(String url, final ResultCallback callback) {
        Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    private String getRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        return syncResult(request);
    }

    /**
     * 构造post 请求
     *
     * @param url 请求的url
     * @param callback 结果回调的方法
     * @param params 请求参数
     */
    private void postRequest(String url, final ResultCallback callback, List<Param> params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    private String postRequest(String url, List<Param> params) {
        Request request = buildPostRequest(url, params);
        return syncResult(request);
    }

    public String syncResult(Request request) {
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            if (response != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 处理请求结果的回调
     */
    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, final IOException e) {
                sendFailCallback(callback, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String str = response.body().string();
                    sendSuccessCallBack(callback, str);
                } catch (final Exception e) {
                    sendFailCallback(callback, e);
                }
            }
        });
    }

    /**
     * 发送失败的回调
     */
    private void sendFailCallback(final ResultCallback callback, final Exception e) {
        if (callback != null) {
            callback.onFailure(e);
        }
    }

    /**
     * 发送成功的调
     */
    private void sendSuccessCallBack(final ResultCallback callback, String obj) {
        if (callback != null) {
            callback.onSuccess(obj);
        }
    }

    /**
     * 构造post请求
     *
     * @param url 请求url
     * @param params 请求的参数
     * @return 返回 Request
     */
    private Request buildPostRequest(String url, List<Param> params) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Param param : params) {
            builder.add(param.key, param.value);
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }


    /**********************对外接口************************/

    /**
     * get请求
     *
     * @param url 请求url
     * @param callback 请求回调
     */
    public static void get(String url, ResultCallback callback) {
        getmInstance().getRequest(url, callback);
    }

    public static String getSync(String url) {
        return getmInstance().getRequest(url);
    }

    /**
     * post请求
     *
     * @param url 请求url
     * @param callback 请求回调
     * @param params 请求参数
     */
    public static void post(String url, final ResultCallback callback, List<Param> params) {
        getmInstance().postRequest(url, callback, params);
    }

    public static String post(String url, List<Param> params) {
       return getmInstance().postRequest(url, params);
    }


    /**
     * http请求回调类,回调方法在UI线程中执行
     */
    public static abstract class ResultCallback {

//        Type mType;
//
//        public ResultCallback() {
//            mType = getSuperclassTypeParameter(getClass());
//        }
//
//        static Type getSuperclassTypeParameter(Class<?> subclass) {
//            Type superclass = subclass.getGenericSuperclass();//返回父类的类型
//            if (superclass instanceof Class) {
//                throw new RunTIMEException("Missing type parameter.");
//            }
//            ParameterizedType parameterized = (ParameterizedType) superclass;
//            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
//        }

        /**
         * 请求成功回调
         */
        public abstract void onSuccess(String result);

        /**
         * 请求失败回调
         */
        public abstract void onFailure(Exception e);
    }


    public static String uploadMultiFile(String url,File file) {
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        RequestBody requestBody = new MultipartBuilder().addFormDataPart("media", null, fileBody).build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        return getmInstance().syncResult(request);
    }

    /**
     * post请求参数类
     */
    public static class Param {

        String key;//请求的参数
        String value;//参数的值

        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }

    public static class ObjectParam {

        String key;//请求的参数
        Object value;//参数的值

        public ObjectParam() {
        }

        public ObjectParam(String key, Object value) {
            this.key = key;
            this.value = value;
        }

    }


}
