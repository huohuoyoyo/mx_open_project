package cn.codemx.customtitle;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import cn.codemx.customtitle.view.CustomTitle;

public class MyActivity extends Activity {

    private CustomTitle mCustomTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mCustomTitle = (CustomTitle) this.findViewById(R.id.main_custom_title);
        mCustomTitle.setTitleBackground(Color.GRAY);
        mCustomTitle.setTitle("自定义标题");
        mCustomTitle.setBackButtonText("返回");
        mCustomTitle.setMoreButtonText("更多");
        mCustomTitle.setOnCustomClickListener(new CustomTitle.OnCustomClickListener() {
            @Override
            public void onBackClick() {
                Toast.makeText(MyActivity.this, "点击“返回”键", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMoreClick() {
                Toast.makeText(MyActivity.this, "点击“更多”键", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
