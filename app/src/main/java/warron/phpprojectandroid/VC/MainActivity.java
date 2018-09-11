package warron.phpprojectandroid.VC;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;


import eu.long1.spacetablayout.TabOnclickLisener;
import warron.phpprojectandroid.Base.BaseActivity;
import warron.phpprojectandroid.Base.CacheTool;
import warron.phpprojectandroid.Base.UserInfoModel;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.ToolbarHelper;
import warron.phpprojectandroid.VC.FragHome.FragHome;
import warron.phpprojectandroid.VC.FragMySelf.FragMySelf;
import warron.phpprojectandroid.VC.GuideModule.GuideActivity;

public class MainActivity extends BaseActivity {
    SpaceTabLayout tabLayout;
    private ToolbarHelper toolbarHelper;

    List<Fragment> fragmentList;//fragment 页卡项目
    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        SharedPreferences.Editor editor = sharedPreferences.edit();//此处表示该应用程序专用
        if (isFirstRun){
            editor.putBoolean("isFirstRun", false);//如果 "isFirstRun"对应的value没有值则默认为true，
            editor.commit();//将isFirstRun写入editor中保存
            startActivity(new Intent(MainActivity.this, GuideActivity.class));
        }
      UserInfoModel model =  CacheTool.getLoginInfoModel();
        if (model!=null && model.isMember != 1){
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            //如果不为0 表示有登录的用户，注意这里最多只会有一个用户的
            // isMember是1 退出的时候将当前用户的isMember置为0
            //isRecentLogin退出登录的时候再设置为1
        }

        fragmentList = new ArrayList<>();
        fragmentList.add(new FragHome());
        final RelativeLayout coordinatorLayout = (RelativeLayout) findViewById(R.id.rlayout);

        List<String> fragTitList = new ArrayList<String>();//标题
        fragTitList.add("会话列表");

        List<Integer> fragImgList = new ArrayList<Integer>();//图片
        fragImgList.add(R.mipmap.conversation_nor);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList,fragTitList,fragImgList);
        tabLayout.setTabOnClickListener(new TabOnclickLisener() {
            @Override
            public void tabOnclick(Integer index, String tabTit) {
                Snackbar snackbar
                        = Snackbar.make(coordinatorLayout, "Welcome to SpaceTabLayout"+ tabLayout.getCurrentPosition(), Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            @Override
            public void tabMoveAction(Integer index, String tabTit) {
                if (tabTit.equals("我的")){
                    setToolBar(tabTit,true);
                    return;
                }
                setToolBar(tabTit,false);
            }
        });
    }

    public void setToolBar(String title,Boolean isShowRightBtn){//切换页卡的时候，改变标题
        toolbarHelper.setTitle(title);
        toolbarHelper.setIsShowRightBtn(isShowRightBtn);//是否显示右边的按钮
    }
    @Override
    protected void initToolBar(final ToolbarHelper toolbarHelper, Boolean isShowBackBtn) {
        super.initToolBar(toolbarHelper,false); // 默认不显示原生标题
        this.toolbarHelper = toolbarHelper;
        toolbarHelper.setTitle("朋友圈");
        toolbarHelper.setMenuTitle("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              ViewPager viewPager =  (ViewPager) findViewById(R.id.viewPager);
              FragMySelf fragMySelf = (FragMySelf) fragmentList.get(viewPager.getCurrentItem());
              if (toolbarHelper.getMenuTitle().equals("编辑")){//原来的状态是未编辑，现在将状态改为 正在编辑
                    toolbarHelper.setMenuTitle("完成",null);
                  fragMySelf.editAction(true);
                }else{
                    toolbarHelper.setMenuTitle("编辑",null);
                 fragMySelf.editAction(false);
                }
            }
        });
    }
}