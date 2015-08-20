package cn.codemx.xutilasync.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import cn.codemx.xutilasync.imp.OnHttpRequestListener;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 网络请求基类
 * Created by MX on 2015/8/20.
 */
public abstract class BaseHttpRequest<T> {

    public static final int GET = 1;
    public static final int POST = 2;
    private static final int REQUEST_SUCCESS = 1001;//网络请求成功码
    private static final int REQUEST_FAILURE = 1002;//网络请求失败码
    private OnHttpRequestListener mListener;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REQUEST_SUCCESS:
                    if (mListener != null) {
                        mListener.onRequestSuccess(msg.obj);
                    }
                    break;
                case REQUEST_FAILURE:
                    if (mListener != null) {
                        mListener.onRequestFailure();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };


    // 拼接url
    protected abstract String onPrepareUrl();

    // GET方式或者POST方式
    protected abstract int onPrepareType();

    // 组装请求参数
    protected abstract RequestParams onPrepareRequestParams();

    // 解析数据
    protected abstract T onParseResponse(String data);

    /**
     * 网络请求
     *
     * @param reqCode  请求标记，需要就用，不需要就不用
     * @param listener 请求监听
     */
    public void start(int reqCode, OnHttpRequestListener listener) {
        mListener = listener;
        int httpMethod = onPrepareType();
        request(reqCode, httpMethod);

    }

    /**
     * 网络请求
     *
     * @param reqCode    请求码
     * @param httpMethod 请求方式
     */
    private void request(int reqCode, final int httpMethod) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (httpMethod) {
                    case GET:
                        requestWithGet();
                        break;
                    case POST:
                        RequestParams params = onPrepareRequestParams();
                        if (params != null) {
                            requestWithPost(params);
                        }
                        break;
                    default:
                        break;
                }
            }
        }).start();

    }

    //GET请求
    public void requestWithGet() {
        String url = onPrepareUrl();
        if (url == null || url.equals("")) {
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        T result = onParseResponse(responseInfo.result);
                        Message msg = Message.obtain();
                        msg.what = REQUEST_SUCCESS;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Message msg = Message.obtain();
                        msg.what = REQUEST_FAILURE;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    //POST请求
    public void requestWithPost(RequestParams params) {
        String url = onPrepareUrl();
        if (url == null || url.equals("")) {
            return;
        }
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        T result = onParseResponse(responseInfo.result);
                        Message msg = Message.obtain();
                        msg.what = REQUEST_SUCCESS;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onFailure(HttpException e, String s) {
                        Message msg = Message.obtain();
                        msg.what = REQUEST_FAILURE;
                        mHandler.sendMessage(msg);
                    }
                });

    }

}
