package cn.codemx.xutilasync.imp;

/**
 * 网络请求监听
 * Created by MX on 2015/8/20.
 */
public interface OnHttpRequestListener {

    void onRequestSuccess(Object o);

    void onRequestFailure();

}
