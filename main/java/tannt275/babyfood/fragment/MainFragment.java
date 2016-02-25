package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tannt275.babyfood.R;

/**
 * Created by TanNT on 12/18/2015.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    public static String TAG = MainFragment.class.getSimpleName();

    private Button rememberBtn;
    private Button nutritionTowerBtn;
    private Button belowOneYearBtn;
    private Button foodsBtn;

    private ButtonListener buttonListener;

    public void setButtonListener(ButtonListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        rememberBtn = (Button) rootView.findViewById(R.id.main_remember_btn);
        nutritionTowerBtn = (Button) rootView.findViewById(R.id.main_remember_nutrition_tower);
        belowOneYearBtn = (Button) rootView.findViewById(R.id.main_below_one_year);
        foodsBtn = (Button) rootView.findViewById(R.id.main_foods);
        rememberBtn.setOnClickListener(this);
        nutritionTowerBtn.setOnClickListener(this);
        belowOneYearBtn.setOnClickListener(this);
        foodsBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()){
            case R.id.main_remember_btn:

                break;
            case R.id.main_remember_nutrition_tower:
                break;
            case R.id.main_below_one_year:
                break;
            case R.id.main_foods:
                break;
        }*/
        buttonListener.onButtonListener(v.getId());
    }

    public interface ButtonListener{
        public void onButtonListener(int idButton);
    }
}
