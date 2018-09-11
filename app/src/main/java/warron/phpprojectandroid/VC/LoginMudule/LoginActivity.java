package warron.phpprojectandroid.VC.LoginMudule;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;

import org.xutils.DbManager;
import org.xutils.db.table.ColumnEntity;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import warron.phpprojectandroid.Base.BaseActivity;
import warron.phpprojectandroid.Base.MyApplication;
import warron.phpprojectandroid.Base.UserInfoModel;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.NetRequestMudule.AsyncHttpNet;
import warron.phpprojectandroid.Tools.NetRequestMudule.DataModel;
import warron.phpprojectandroid.VC.MainActivity;


public class LoginActivity extends BaseActivity {
    private EditText phoneTextView;
    private EditText passTextView;

    @Event(value = R.id.loginBtn,
            type = View.OnClickListener.class/*可选参数, 默认是View.OnClickListener.class*/)
    @Override
    public int getContentView() {
        return R.layout.login_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneTextView = (EditText) findViewById(R.id.phoneTexView);
        passTextView = (EditText) findViewById(R.id.passTexView);
    }

    private boolean isNetworkAvailable() {
        // 得到网络连接信息
        ConnectivityManager manager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        // 去进行判断网络是否连接
        if (manager.getActiveNetworkInfo() != null) {
            return manager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

//    @Override
    protected void initToolBar() {
    }

    public void loginBtnClick(View view) {

        AsyncHttpNet net = new AsyncHttpNet() {
            @Override
            public void onSuccess(String result) {
                UserInfoModel model = JSON.parseObject(result, UserInfoModel.class);
                model.isMember = 1;
                model.isRecentLogin = 0;//isRecentLogin退出登录的时候再设置为1
                DbManager manager = ((MyApplication) x.app()).manager;

                try {
                    TableEntity<UserInfoModel> entity = manager.getTable(UserInfoModel.class);
                    LinkedHashMap<String, ColumnEntity> columnMap = entity.getColumnMap();
                    Map<String,String> map = JSON.parseObject(result, Map.class);
                    for (String column : map.keySet()) {
                        if (!columnMap.containsKey(column)){
                            manager.addColumn(UserInfoModel.class,column);//如果不存在列就添加 同时要在UserInfoModel中添加字段
                        }
                    }
                    // manager.selector(UserInfoModel.class).orderBy("id", true).limit(1000).findAll();
                    List<UserInfoModel> list = manager.selector(UserInfoModel.class)
                            .where("keyId", "=", model.keyId)
                            .orderBy("keyId")
                            .limit(10).findAll();

                    if ( list == null ||list.size() == 0 ) {//如果不存在的话 就添加
                        manager.save(model);
                    } else {//否则就添加诺
                        manager.update(model);
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        };
        DataModel model = new DataModel();//这个类是请求数据模型，请求参数可以写到这个类中，AsyncHttpNet中会将模型转为字典
        model.mobile = "18408246307";//18408246313 18408246307 18408246313 18408246305
        phoneTextView.getText().toString();
        model.password = "123456";
        passTextView.getText().toString();
        net.post(model, "login.php");
    }

    public void forgetBtnClick(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPassAC.class));
    }

    public void registerBtnClick(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void isShowPassBtnClick(View view) {
        Button btn = (Button) view;
        if (passTextView.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            btn.setBackgroundResource(R.mipmap.b_ic_yanjingb);
            passTextView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            btn.setBackgroundResource(R.mipmap.b_ic_yanjing);
            passTextView.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    public void qqBtnClick(View view) {

    }

    public void weChatBtnClick(View view) {

    }

    public void protocolBtnClick(View view) {

    }
}
