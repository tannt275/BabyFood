package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import tannt275.babyfood.R;
import tannt275.babyfood.model.FoodModel;

public class ReadingFoodsFragment extends Fragment {

    public static String TAG = ReadingFoodsFragment.class.getSimpleName();

    public static String FOOD_DATA = "FOOD_DATA";

    private TextView time;
    private TextView name;
    private TextView material;
    private TextView method;

    private FoodModel foodModel;

    public static ReadingFoodsFragment newInstance(FoodModel foodModel) {
        ReadingFoodsFragment fragment = new ReadingFoodsFragment();
        Bundle args = new Bundle();
        args.putString(FOOD_DATA, foodModel.convertToString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodModel = new Gson().fromJson(getArguments().getString(FOOD_DATA), FoodModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reading_foods, container, false);
        time = (TextView) rootView.findViewById(R.id.fragment_reading_foods_time);
        name = (TextView) rootView.findViewById(R.id.fragment_reading_foods_name);
        material = (TextView) rootView.findViewById(R.id.fragment_reading_foods_material);
        method = (TextView) rootView.findViewById(R.id.fragment_reading_foods_method);
        fillData();
        return rootView;
    }

    private void fillData() {
        time.setText(String.format(getString(R.string.reading_foods_fragment_time_prefix), foodModel.get_timesFood()));
        name.setText(foodModel.get_nameFood());
        material.setText(foodModel.get_materialContent());
        method.setText(foodModel.get_methodContent());
    }
}
