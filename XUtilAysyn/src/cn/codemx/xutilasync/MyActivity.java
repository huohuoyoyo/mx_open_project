package cn.codemx.xutilasync;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import cn.codemx.xutilasync.async.GetPersonHttpRequest;
import cn.codemx.xutilasync.bean.Person;

public class MyActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetPersonHttpRequest("moxiang", "man", "30", mListener).startRequest();
            }
        });
    }

    private GetPersonHttpRequest.OnPersonRequestListener mListener = new GetPersonHttpRequest.OnPersonRequestListener() {
        @Override
        public void onSuccess(Person person) {

        }

        @Override
        public void onFailure() {

        }
    };
}
