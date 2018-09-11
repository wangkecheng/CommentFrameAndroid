package warron.phpprojectandroid.Base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.githang.statusbar.StatusBarCompat;

import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.ToolbarHelper;

/**
 * Created by holenzhou on 2016/12/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {

            initToolBar(new ToolbarHelper(toolbar),true);
        }
        Window window = this.getWindow();
        //设置顶部状态栏颜色
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.SysThemeColor));
        //设置底部导航栏颜色
        window.setNavigationBarColor(this.getResources().getColor(R.color.SysThemeColor));
    }

    public abstract int getContentView();
    protected void initToolBar(ToolbarHelper toolbarHelper,Boolean isShowBackBtn) {
        setSupportActionBar(toolbar);//是否支持 ，这里必须设置 否则不能隐藏默认标题等
        // 默认不显示原生标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        // 显示应用的Logo
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);

        // 显示标题和子标题
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        toolbar.setTitle("ToolbarDemo");
//        toolbar.setSubtitle("the detail of toolbar");

         if (isShowBackBtn){// 显示返回按钮
             toolbar.setNavigationIcon(R.drawable.icon_back);
             toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     finish();
                 }
             });
         }
    }

    //获取状态栏高度
    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
