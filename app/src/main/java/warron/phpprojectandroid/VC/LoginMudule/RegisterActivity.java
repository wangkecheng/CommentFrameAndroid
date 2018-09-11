package warron.phpprojectandroid.VC.LoginMudule;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.EditText;

import warron.phpprojectandroid.Base.BaseActivity;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.NetRequestMudule.AsyncHttpNet;
import warron.phpprojectandroid.Tools.NetRequestMudule.DataModel;


public class RegisterActivity extends BaseActivity {
    private EditText phoneTextView;
    private EditText passTextView;
    private EditText verPassTextView;
    private EditText verCodeTextView;
    @Override
    public int getContentView() {
        return R.layout.register_layout;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        phoneTextView = (EditText) findViewById(R.id.registPassPassTextView);
        verCodeTextView = (EditText) findViewById(R.id.registVerCodeTextView);
        passTextView = (EditText) findViewById(R.id.registPassPassTextView);
        verPassTextView = (EditText) findViewById(R.id.registPassVerPassTextView);
    }

//    @Override
    protected void initToolBar() {

//        super.initToolBar();
        toolbar.setTitle("注册");
    }
    public void registerSendVerCodeBtnClick(View view){

    }
    public void registerBtnClick(View view){

        AsyncHttpNet net = new AsyncHttpNet(){

            @Override
            public void onSuccess(String result) {

                finish();
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        };
        DataModel model = new DataModel();//这个类是请求数据模型，请求参数可以写到这个类中，AsyncHttpNet中会将模型转为字典
        model.mobile = phoneTextView.getText().toString();
        model.password = passTextView.getText().toString();
        model.vertifyPass = verPassTextView.getText().toString();
        net.post(model,"register.php");
    }
}
