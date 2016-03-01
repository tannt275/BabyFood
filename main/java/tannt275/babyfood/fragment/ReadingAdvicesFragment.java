package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import tannt275.babyfood.R;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.model.AdvicesModel;

public class ReadingAdvicesFragment extends Fragment {

    public static String TAG = ReadingAdvicesFragment.class.getSimpleName();
    public static String ADVICES_DATA="ADVICES_DATA";

    private ImageView addIcon;
    private ImageView favoriteIcon;
    private ImageView shareIcon;
    private ImageView deleteIcon;
    private ImageView urlAdvices;
    private TextView nameAdvices;
    private TextView contentAdvices;

    private AdvicesModel advicesModel;

    public static ReadingAdvicesFragment newInstance(AdvicesModel advicesModel) {
        ReadingAdvicesFragment fragment = new ReadingAdvicesFragment();
        Bundle args = new Bundle();
        args.putString(ADVICES_DATA, advicesModel.convertToString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            advicesModel = new Gson().fromJson(getArguments().getString(ADVICES_DATA),AdvicesModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reading_advices, container, false);
        addIcon = (ImageView) rootView.findViewById(R.id.addImage);
        favoriteIcon = (ImageView) rootView.findViewById(R.id.favoriteImage);
        shareIcon = (ImageView) rootView.findViewById(R.id.shareImage);
        deleteIcon = (ImageView) rootView.findViewById(R.id.deleteImage);
        urlAdvices = (ImageView) rootView.findViewById(R.id.urlAdvices);
        nameAdvices = (TextView) rootView.findViewById(R.id.nameAdvices);
        contentAdvices = (TextView) rootView.findViewById(R.id.contentAdvices);
        fillData();
        return rootView;
    }

    private void fillData() {
        ImageLoader.getInstance().displayImage(advicesModel.get_url(), urlAdvices, AppUtils.OPTION_IMAGE);
        nameAdvices.setText(advicesModel.get_name());
        contentAdvices.setText(advicesModel.get_content());

        addIcon.setOnClickListener(addToDataListener);
        deleteIcon.setEnabled(advicesModel.get_admin() == 1);
        shareIcon.setOnClickListener(shareDataListener);
        favoriteIcon.setOnClickListener(favoriteListener);
    }

    private View.OnClickListener addToDataListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener shareDataListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener favoriteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
}
