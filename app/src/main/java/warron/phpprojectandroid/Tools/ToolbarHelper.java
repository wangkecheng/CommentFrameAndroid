package warron.phpprojectandroid.Tools;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import warron.phpprojectandroid.R;

/**
 * Created by zhangtao on 2016/11/8.
 */
public class ToolbarHelper {
    private Toolbar mToolbar;

    public ToolbarHelper(Toolbar toolbar) {
        this.mToolbar = toolbar;

    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setTitle(String title) {
        TextView titleTV = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        titleTV.setText(title);
    }

    //设置toolBar 右侧title 及点击事件
    public void setMenuTitle(String menuTitle, View.OnClickListener listener) {
        TextView textView = (TextView) mToolbar.findViewById(R.id.toolbar_menu_title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(menuTitle);
        if (listener != null) {
            textView.setOnClickListener(listener);//不为空的时候才加监听
        }
    }

    public String getMenuTitle(){
        TextView textView = (TextView) mToolbar.findViewById(R.id.toolbar_menu_title);
        return textView.getText().toString();
    }

    //设置toolBar 右侧图片 及点击事件
    public void setMenuImg(int imgId, View.OnClickListener listener) {
        Button button = (Button) mToolbar.findViewById(R.id.toolbar_menu_btn);
        button.setVisibility(View.VISIBLE);
        button.setBackgroundResource(imgId);
        button.setOnClickListener(listener);
    }

    public void setIsShowRightBtn(boolean isShowRightBtn) {

        TextView textView = (TextView) mToolbar.findViewById(R.id.toolbar_menu_title);
        textView.getVisibility();
        if (textView.getText().length() > 0) {
            textView.setVisibility(View.GONE);
            if (isShowRightBtn) {
                textView.setVisibility(View.VISIBLE);
            }
        }
        Button button = (Button) mToolbar.findViewById(R.id.toolbar_menu_btn);
        if (button.getText().length() > 0) {
            button.setVisibility(View.GONE);
            if (isShowRightBtn) {
                button.setVisibility(View.VISIBLE);
            }
        }
    }
}
