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
 * �����������
 * Created by MX on 2015/8/20.
 */
public abstract class BaseHttpRequest<T> {

    public static final int GET = 1;
    public static final int POST = 2;
    private static final int REQUEST_SUCCESS = 1001;//��������ɹ���
    private static final int REQUEST_FAILURE = 1002;//��������ʧ����
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


    // ƴ��url
    protected abstract String onPrepareUrl();

    // GET��ʽ����POST��ʽ
    protected abstract int onPrepareType();

    // ��װ�������
    protected abstract RequestParams onPrepareRequestParams();

    // ��������
    protected abstract T onParseResponse(String data);

    /**
     * ��������
     *
     * @param reqCode  �����ǣ���Ҫ���ã�����Ҫ�Ͳ���
     * @param listener �������
     */
    public void start(int reqCode, OnHttpRequestListener listener) {
        mListener = listener;
        int httpMethod = onPrepareType();
        request(reqCode, httpMethod);

    }

    /**
     * ��������
     *
     * @param reqCode    ������
     * @param httpMethod ����ʽ
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

    //GET����
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

    //POST����
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
