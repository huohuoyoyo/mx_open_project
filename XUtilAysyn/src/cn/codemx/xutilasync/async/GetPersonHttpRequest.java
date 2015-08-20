package cn.codemx.xutilasync.async;

import cn.codemx.xutilasync.imp.OnHttpRequestListener;
import cn.codemx.xutilasync.bean.Person;
import cn.codemx.xutilasync.util.UrlUtil;
import com.lidroid.xutils.http.RequestParams;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取person对象的网络请求类
 * Created by MX on 2015/8/20.
 */
public class GetPersonHttpRequest extends BaseHttpRequest<Person> {

    private String mName;
    private String mSex;
    private String mAge;
    private OnPersonRequestListener mListener;

    public GetPersonHttpRequest(String name, String sex, String age, OnPersonRequestListener listener) {
        this.mName = name;
        this.mSex = sex;
        this.mAge = age;
        this.mListener = listener;
    }

    //开始请求数据
    public void startRequest() {
        super.start(0, mRequestListener);
    }

    /**
     * 网络请求接口
     *
     * @return 网络请求接口
     */
    @Override
    protected String onPrepareUrl() {
        return UrlUtil.GET_PERSON_URL;
    }

    /**
     * 网络请求类型
     *
     * @return GET，POST
     */
    @Override
    protected int onPrepareType() {
        return GET;
    }

    /**
     * POST请求需要的参数，GET请求返回null即可
     *
     * @return RequestParams
     */
    @Override
    protected RequestParams onPrepareRequestParams() {
        RequestParams params = new RequestParams();
        params.addBodyParameter("name", mName);
        params.addBodyParameter("sex", mSex);
        params.addBodyParameter("age", mAge);
        return params;
    }

    /**
     * 解析网络请求回来的数据
     *
     * @param data 网络请求回来的数据
     * @return 需要的对象
     */
    @Override
    protected Person onParseResponse(String data) {
        if (data == null || data.equals("")) {
            return null;
        }
        Person person = null;
        try {
            JSONObject jsonObject = new JSONObject(data);
            person = new Person();
            person.setName(jsonObject.getString("name"));
            person.setSex(jsonObject.getString("sex"));
            person.setAge(jsonObject.getString("age"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    //网络请求结果监听
    private OnHttpRequestListener mRequestListener = new OnHttpRequestListener() {
        @Override
        public void onRequestSuccess(Object o) {
            if (mListener != null) {
                mListener.onSuccess((Person) o);
            }
        }

        @Override
        public void onRequestFailure() {
            if (mListener != null) {
                mListener.onFailure();
            }
        }
    };

    public interface OnPersonRequestListener {

        void onSuccess(Person person);

        void onFailure();

    }

}
