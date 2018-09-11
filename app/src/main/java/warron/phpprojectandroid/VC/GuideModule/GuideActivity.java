package warron.phpprojectandroid.VC.GuideModule;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;
import warron.phpprojectandroid.R;
public class GuideActivity extends MaterialIntroActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getBackButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });
        GuideFrag guideFragOne = new GuideFrag(R.mipmap.pic_denglu);
        addSlide(guideFragOne);

        GuideFrag guideFragTwo = new GuideFrag(R.mipmap.pic_denglu);
        addSlide(guideFragTwo);

        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.SysThemeColor)
                        .buttonsColor(R.color.SysThemeColor)
                        .possiblePermissions(new String[]{Manifest.permission.CALL_PHONE})
                        .neededPermissions(new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.INTERNET,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE})
                        .image(R.mipmap.pic_denglu)
                        .title("")
                        .description("")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("");
                    }
                }, "开始授权"));

        GuideFrag guideFragThree = new GuideFrag(R.mipmap.pic_denglu);
        addSlide(guideFragThree);
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Toast.makeText(this, "欢迎进入校园meme,开始你的精彩生活", Toast.LENGTH_SHORT).show();

    }

}
