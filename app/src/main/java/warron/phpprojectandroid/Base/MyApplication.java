package warron.phpprojectandroid.Base;

import android.app.Application;
import android.os.Environment;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

  public  DbManager manager;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 开启debug会影响性能

        // 全局默认信任所有https域名 或 仅添加信任的https域名
        // 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        DbManager.DaoConfig daoConfig =  new DbManager.DaoConfig()
                .setDbName("SchoolMeMe.db") //设置数据库名，默认xutils.db
                .setDbDir(new File(Environment.getExternalStorageDirectory().getAbsolutePath())) //设置数据库路径，默认存储在app的私有目录
                .setDbVersion(2) //设置数据库的版本号
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override//设置数据库打开的监听
                    public void onDbOpened(DbManager db) { //开启数据库支持多线程操作，提升性能，对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setAllowTransaction(true)//设置是否允许事务，默认true
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override //设置表创建的监听
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                    }
        }); //这里创建数据库
        manager = x.getDb(daoConfig);
    }
}
