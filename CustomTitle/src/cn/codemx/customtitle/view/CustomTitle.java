package cn.codemx.customtitle.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.codemx.customtitle.R;

/**
 * 自定义标题布局
 * Created by MX on 2015/9/4.
 */
public class CustomTitle extends RelativeLayout implements View.OnClickListener {

    private RelativeLayout mRl;
    private Button mBtnBack;
    private Button mBtnMore;
    private TextView mTvTitle;

    public CustomTitle(Context context) {
        super(context);
        init(context);
    }

    public CustomTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //int view
    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_title, this, true);
        mRl = (RelativeLayout) view.findViewById(R.id.rl_custom_title);
        mBtnBack = (Button) view.findViewById(R.id.btn_custom_title_back);
        mBtnMore = (Button) view.findViewById(R.id.btn_custom_title_more);
        mTvTitle = (TextView) view.findViewById(R.id.tv_custom_title);
        mBtnBack.setOnClickListener(this);
        mBtnMore.setOnClickListener(this);
    }

    //设置整个标题栏背景颜色
    public void setTitleBackground(int color) {
        if (mRl != null) {
            mRl.setBackgroundColor(color);
        }
    }

    //设置按钮文字
    public void setBackButtonText(int resId) {
        if (mBtnBack != null) {
            mBtnBack.setText(resId);
        }
    }

    //设置按钮文字
    public void setBackButtonText(String character) {
        if (mBtnBack != null) {
            mBtnBack.setText(character);
        }
    }

    //设置按钮文字颜色
    public void setBackButtonColor(int color) {
        if (mBtnBack != null) {
            mBtnBack.setTextColor(color);
        }
    }

    //设置左边按钮的背景
    public void setBackButtonBackground(int resId) {
        if (mBtnBack != null) {
            mBtnBack.setBackgroundResource(resId);
        }
    }

    //设置左边按钮的背景
    public void setBackButtonBackground(Drawable drawable) {
        if (mBtnBack != null) {
            mBtnBack.setBackground(drawable);
        }
    }

    //设置左边按钮的背景
    public void setBackButtonBackgroundColor(int color) {
        if (mBtnBack != null) {
            mBtnBack.setBackgroundColor(color);
        }
    }

    //设置标题文字
    public void setTitle(int resId) {
        if (mTvTitle != null) {
            mTvTitle.setText(resId);
        }
    }

    //设置标题文字
    public void setTitle(String character) {
        if (mTvTitle != null) {
            mTvTitle.setText(character);
        }
    }

    //设置标题文字颜色
    public void setTitleColor(int color) {
        if (mTvTitle != null) {
            mTvTitle.setTextColor(color);
        }
    }

    //设置标题字体大小
    public void setTitleSize(float size) {
        if (mTvTitle != null) {
            mTvTitle.setTextSize(size);
        }
    }

    //设置右侧按钮文字
    public void setMoreButtonText(int resId) {
        if (mBtnMore != null) {
            mBtnMore.setText(resId);
        }
    }

    //设置右侧按钮文字
    public void setMoreButtonText(String character) {
        if (mBtnMore != null) {
            mBtnMore.setText(character);
        }
    }

    //设置右侧按钮文字颜色
    public void setMoreButtonTextColor(int color) {
        if (mBtnMore != null) {
            mBtnMore.setTextColor(color);
        }
    }

    //设置右侧按钮背景
    public void setMoreButtonBackground(int resId) {
        if (mBtnMore != null) {
            mBtnMore.setBackgroundResource(resId);
        }
    }

    //设置右侧按钮背景
    public void setMoreButtonBackground(Drawable drawable) {
        if (mBtnMore != null) {
            mBtnMore.setBackground(drawable);
        }
    }

    //设置右侧按钮背景
    public void setMoreButtonBackgroundColor(int color) {
        if (mBtnMore != null) {
            mBtnMore.setBackgroundColor(color);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_custom_title_back: // 左侧按钮点击事件回掉
                if (mOnCustomClickListener != null) {
                    mOnCustomClickListener.onBackClick();
                }
                break;
            case R.id.btn_custom_title_more: // 右侧按钮点击事件回调
                if (mOnCustomClickListener != null) {
                    mOnCustomClickListener.onMoreClick();
                }
                break;
            default:
                break;
        }
    }

    private OnCustomClickListener mOnCustomClickListener;

    public void setOnCustomClickListener(OnCustomClickListener onCustomClickListener) {
        mOnCustomClickListener = onCustomClickListener;
    }

    public interface OnCustomClickListener {

        void onBackClick();

        void onMoreClick();

    }

}
