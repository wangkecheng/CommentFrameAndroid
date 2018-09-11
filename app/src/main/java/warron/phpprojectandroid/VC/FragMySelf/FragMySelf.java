package warron.phpprojectandroid.VC.FragMySelf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;
import com.mingle.entity.MenuEntity;
import com.mingle.sweetpick.BlurEffect;
import com.mingle.sweetpick.RecyclerViewDelegate;
import com.mingle.sweetpick.SweetSheet;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import warron.phpprojectandroid.Base.BaseFragment;
import warron.phpprojectandroid.Base.CacheTool;
import warron.phpprojectandroid.Base.UserInfoModel;
import warron.phpprojectandroid.R;
import warron.phpprojectandroid.Tools.GlideCircleTransform;
import warron.phpprojectandroid.Tools.NetRequestMudule.AsyncHttpNet;
import warron.phpprojectandroid.Tools.NetRequestMudule.DataModel;
import warron.phpprojectandroid.Tools.RoundImageView;


public class FragMySelf extends BaseFragment implements View.OnClickListener, OnDateSetListener {
    private RoundImageView headerImg;
    private TextView nickname;
    private EditText nameEditText;
    private EditText nicknameEditText;
    private Button birthdayBtn;
    private Button sexBtn;
    private Button schoolBtn;
    private Button majorBtn;
    private EditText classEditText;

    View view;
    TimePickerDialog mDialogYearMonthDay;
    TextView mTvTime;

    private SweetSheet mSweetSheet;//上传图片的sheet框
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_myself, null);

        headerImg = view.findViewById(R.id.myself_header_img);//头像
        headerImg.setmBorderRadius(headerImg.getLayoutParams().width / 2);
        nickname = view.findViewById(R.id.myself_nickname);//昵称
        nameEditText = view.findViewById(R.id.myself_nameEditText);//修改姓名
        nicknameEditText = view.findViewById(R.id.myself_nicknameEditText);//修改昵称
        birthdayBtn = view.findViewById(R.id.myself_birthdayBtn);//生日
        sexBtn = view.findViewById(R.id.myself_sexBtn);//性别
        schoolBtn = view.findViewById(R.id.myself_schoolBtn);//学校
        majorBtn = view.findViewById(R.id.myself_majorBtn);//专业
        classEditText = view.findViewById(R.id.myself_classEditText);//班级

        headerImg.setOnClickListener(this);
        birthdayBtn.setOnClickListener(this);
        sexBtn.setOnClickListener(this);
        schoolBtn.setOnClickListener(this);
        majorBtn.setOnClickListener(this);
        initData();
        initEditAble(false);//默认是不可用
        return view;

    }

    public void initData() {
        headerImg = view.findViewById(R.id.myself_header_img);//头像
        UserInfoModel model = CacheTool.getLoginInfoModel();

        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
                .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
                .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片
                .build();
        x.image().bind(headerImg, model.headImg, imageOptions);
        nickname.setText(model.nickName);
        nameEditText.setText(model.userName);
        birthdayBtn.setText(model.birthday);
        sexBtn.setText(model.sex);
        schoolBtn.setText(model.university);
        majorBtn.setText(model.major);
        classEditText.setText(model.className);

    }
 public void initEditAble(boolean isStartEdit){
     headerImg.setEnabled(isStartEdit);
     nameEditText.setEnabled(isStartEdit);
     nicknameEditText.setEnabled(isStartEdit);
     birthdayBtn.setEnabled(isStartEdit);
     sexBtn.setEnabled(isStartEdit);
     schoolBtn.setEnabled(isStartEdit);
     majorBtn.setEnabled(isStartEdit);
     classEditText.setEnabled(isStartEdit);
 }
    public void editAction(boolean isStartEdit) {//是否开始编辑
        initEditAble(isStartEdit);
        if (!isStartEdit && mSweetSheet != null && mSweetSheet.isShow()) {//正在显示的状态就得 关掉弹窗
            mSweetSheet.dismiss();
        }else if(isStartEdit==false){
            AsyncHttpNet httpNet = new AsyncHttpNet() {
                @Override
                public void onSuccess(String result) {

                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }
            };
            UserInfoModel model =  CacheTool.getLoginInfoModel();
            model.nickName = nicknameEditText.getText().toString();
            model.userName = nameEditText.getText().toString();
            model.className = classEditText.getText().toString();

            model = CacheTool.getLoginInfoModel();
            httpNet.post(model, "updateuserinfo.php");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myself_header_img: {//选择头像

                setupSheepView(true);//弹出的是相机和相册
                if (mSweetSheet.isShow())
                    mSweetSheet.dismiss();
                else
                    mSweetSheet.show();
            }
            break;
            case R.id.myself_birthdayBtn: {
                selectBirthday();//选择生日
            }
            break;
            case R.id.myself_sexBtn: {//性别
                setupSheepView(false);//弹出的是男和女
                if (mSweetSheet.isShow())
                    mSweetSheet.dismiss();
                else
                    mSweetSheet.show();
            }
            break;
            case R.id.myself_schoolBtn: {//进入学校选择界面

            }
            break;
            case R.id.myself_majorBtn: {//进入主修课程选择界面

            }
            break;
        }
    }

    private void setupSheepView(final Boolean isSetHeadImg) {
        final RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rlayout);
        // SweetSheet 控件,根据 rl 确认位置
        if (mSweetSheet == null) {
            mSweetSheet = new SweetSheet(rl);
        }

        MenuEntity album = new MenuEntity();
        album.titleColor = 0xffb3b3b3;

        final MenuEntity takePhoto = new MenuEntity();
        takePhoto.titleColor = 0xffb3b3b3;

        MenuEntity dismiss = new MenuEntity();
        dismiss.titleColor = 0xffb3b3b3;
        dismiss.title = "取消";
        if (isSetHeadImg) {
            album.iconId = R.mipmap.ic_xiangche;
            album.title = "相册";

            takePhoto.title = "拍照";
            takePhoto.iconId = R.mipmap.ic_xiangji;
        } else {
            album.iconId = R.mipmap.ic_xiangche;
            album.title = "男";

            takePhoto.title = "女";
            takePhoto.iconId = R.mipmap.ic_xiangji;
        }
        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        final ArrayList<MenuEntity> list = new ArrayList<MenuEntity>();
        list.add(album);
        list.add(takePhoto);
        list.add(dismiss);
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(false));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                //即时改变当前项的颜色
//                list.get(position).titleColor = 0xff5823ff;
                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
                File directory = new File(Environment.getExternalStorageDirectory(), "warron/warron.jpg");
                //                    Bitmap bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(directory.getPath()));
//                    ((ImageView) view.findViewById(R.id.imageViewT)).setImageBitmap(bitmap1);// 将图片显示在ImageView里
                if (isSetHeadImg) {
                    if (position == 0) {
                        selectPhotoInAlbum();
                    } else if (position == 1) {
                        takePhotoAction();
                    } else {
                        mSweetSheet.dismiss();
                    }
                } else {
                    UserInfoModel model = CacheTool.getLoginInfoModel();
                    if (position == 0) {
                        model.setSex("男");
                    } else if (position == 1) {
                        model.setSex("女");
                    } else {
                        mSweetSheet.dismiss();
                    }
                    sexBtn.setText(model.sex);
                    CacheTool.saveOrUpdate(model);
                }
                return true; //true 会自动关闭.
            }
        });
    }

    private void selectBirthday() {//选择生日日期
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setCallBack(this)
                .setCancelStringId("取消")
                .setSureStringId("确定")
                .setTitleStringId("选择时间")
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(12)
                .build();
        mDialogYearMonthDay.show(getActivity().getSupportFragmentManager(), Type.YEAR_MONTH_DAY.toString());
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {

        UserInfoModel model = CacheTool.getLoginInfoModel();
        model.birthday = getDateToString(millseconds);
        birthdayBtn.setText(model.birthday);
        CacheTool.saveOrUpdate(model);
    }

    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }

    private void selectPhotoInAlbum() {
//        getActivity 获取当前的
        PhotoPicker.builder()
                .setPhotoCount(1)//设置上传照片最大数
                .start((Activity) getActivity());
    }

    private void takePhotoAction() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override //选择图片后的回调
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        ArrayList<File> list = new ArrayList<File>();
        if (resultCode == -1 &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                bitmap = BitmapFactory.decodeFile(photos.get(0), null);
            }
            for (int i = 0; i < photos.size(); i++) {
                if (photos.get(i) instanceof String) {//如果是字符串类型
                    File file = new File(photos.get(i));
                    list.add(file);
                }
            }
        } else if (resultCode == Activity.RESULT_OK) {//
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.i("warron", "SD卡不可用 请检查权限");
                return;
            }
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

            //创建本地文件夹 写入外置内存卡
            File directory = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
            if (!directory.exists())
                directory.mkdirs();//这里用这个好一些
            //创建文件
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss",
                    Locale.getDefault());
            File file = new File(directory, dataFormat.format(new Date()) + "_meme.jpg");
            if (file.exists())
                file.delete();//删除文件
            //设置图片格式
            Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    OutputStream stream = new FileOutputStream(file);
                    if (bitmap != null) {
                        bitmap.compress(format, 100, stream);
                    }
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            list.add(file);
        }
        if (bitmap != null) {

            headerImg.setImageBitmap(bitmap);// 将图片显示在ImageView里
        }
        uploadImg(list);
    }

    private void uploadImg(ArrayList<File> imgFileList) {//可以上传多张

        AsyncHttpNet net = new AsyncHttpNet() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                UserInfoModel model = CacheTool.getLoginInfoModel();
                model.headImg = result;
              CacheTool.saveOrUpdate(model);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        };
        DataModel model = new DataModel();
        model.keyId = "18408246301";
        model.files = new ArrayList<File>();
        model.files = imgFileList;
        net.uploadImg(model, "uploadimgs.php");
    }

    public void settingBtnClick(){//设置按钮 进入设置界面

    }
}
