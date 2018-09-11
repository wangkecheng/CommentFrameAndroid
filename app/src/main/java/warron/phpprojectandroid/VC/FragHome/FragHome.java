package warron.phpprojectandroid.VC.FragHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import warron.phpprojectandroid.Base.BaseFragment;
import warron.phpprojectandroid.R;

public class FragHome extends BaseFragment implements View.OnClickListener{
    View view;
    Button btnHomeSetting;
    Button btnHomeExportStatistics;
    Button btnHomeExportAll;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_home, null);

        btnHomeSetting = view.findViewById(R.id.btn_home_setting);
        btnHomeSetting.setOnClickListener(this);

        btnHomeExportStatistics = view.findViewById(R.id.btn_home_exportStatistics);
        btnHomeExportStatistics.setOnClickListener(this);

        btnHomeExportAll = view.findViewById(R.id.btn_home_ExportAll);
        btnHomeExportAll.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_home_setting: {
//                Toast.makeText(getContext(), "123", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.btn_home_exportStatistics: {

//                Toast.makeText(getContext(), "123", Toast.LENGTH_LONG).show();
            }
            break;
            case R.id.btn_home_ExportAll: {

//                Toast.makeText(getContext(), "123", Toast.LENGTH_LONG).show();
            }
            break;
        }
    }
}
