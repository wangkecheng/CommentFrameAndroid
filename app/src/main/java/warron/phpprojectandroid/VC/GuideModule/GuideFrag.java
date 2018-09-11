package warron.phpprojectandroid.VC.GuideModule;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import agency.tango.materialintroscreen.SlideFragment;
import warron.phpprojectandroid.R;

@SuppressLint("ValidFragment")
public class GuideFrag extends SlideFragment {
    private ImageView imageView;
    private int imgId;

    @SuppressLint("ValidFragment")
    public GuideFrag(int imgId){
        this.imgId = imgId;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_guide, container, false);
        imageView = (ImageView) view.findViewById(R.id.guide_back_img);
        imageView.setImageResource(imgId);
        return view;
    }
    @Override
    public int backgroundColor() {
        return R.color.SysThemeColor;
    }

    @Override
    public int buttonsColor() {
        return R.color.SysThemeColor;
    }
}
