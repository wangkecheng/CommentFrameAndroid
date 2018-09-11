package warron.phpprojectandroid.Base;

import android.app.Application;
import android.content.Intent;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import warron.phpprojectandroid.VC.LoginMudule.LoginActivity;
import warron.phpprojectandroid.VC.MainActivity;

public class CacheTool {

    public static DbManager getDBManager(){
        return ((MyApplication)x.app()).manager;
    }
    public static UserInfoModel getLoginInfoModel() {
        DbManager manager  =  CacheTool.getDBManager();
        try {
            List<UserInfoModel> list = manager.selector(UserInfoModel.class)
                    .where("isMember", "=", 1)
                    .orderBy("isMember")
                    .findAll();

            if (list != null && list.size() != 0) {
                //如果为0 表示有登录的用户，注意这里最多只会有一个用户的
                // isMember是1 退出的时候将当前用户的isMember置为0
                //isRecentLogin退出登录的时候再设置为1
                return list.get(0);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return new UserInfoModel();//返回一个所有字段都为空的model
    }
    public static void saveOrUpdate(Object object){
        try {
            getDBManager().saveOrUpdate(object);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
