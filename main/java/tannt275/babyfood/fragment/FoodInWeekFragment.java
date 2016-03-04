package tannt275.babyfood.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.adapter.FoodInWeekFragmentAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.FoodInWeekModel;
import tannt275.babyfood.model.FoodsDay;

public class FoodInWeekFragment extends Fragment {

    public static String TAG = FoodInWeekFragment.class.getSimpleName();

    private ViewPager viewPager;
    private DatabaseHandler databaseHandler;
    private List<FoodInWeekModel> foodInWeekModelList;
    private AdapterFoodInWeek adapterFoodInWeek;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        foodInWeekModelList = databaseHandler.getFoodInWeek();
        databaseHandler.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_food_in_week, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.food_in_week_viewPager);
        fillDataToViewPager();
        return rootView;
    }

    private void fillDataToViewPager() {

        List<Integer> listResouce = new ArrayList<>();
        listResouce.add(R.mipmap.monday);
        listResouce.add(R.mipmap.tuesday);
        listResouce.add(R.mipmap.wednesday);
        listResouce.add(R.mipmap.thursday);
        listResouce.add(R.mipmap.friday);
        listResouce.add(R.mipmap.saturday);
        listResouce.add(R.mipmap.sunday);

        for (int i = 0; i < foodInWeekModelList.size(); i++) {
            FoodInWeekModel foodInWeekModel = foodInWeekModelList.get(i);
            foodInWeekModel.set_idResource(listResouce.get(i));
        }
        adapterFoodInWeek = new AdapterFoodInWeek(getChildFragmentManager(), foodInWeekModelList);
        viewPager.setAdapter(adapterFoodInWeek);

        int day = dayOfWeek();

        viewPager.setCurrentItem(day - 2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private int dayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Log.e(TAG, "day of week is: " + dayOfWeek);
        return dayOfWeek;
    }

    public static class FoodInWeekFragmentItem extends Fragment {

        private DatabaseHandler databaseHandler;
        private ImageView imageView;
        private ListView listItem;
//        private TextView content;?
        private FoodInWeekModel foodInWeekModel;

        public static FoodInWeekFragmentItem newInstance(FoodInWeekModel foodInWeekModel) {
            FoodInWeekFragmentItem foodInWeekFragmentItem = new FoodInWeekFragmentItem();
            Bundle args = new Bundle();
            args.putString(AppUtils.DATA_FOOD_IN_WEEK_ITEM, foodInWeekModel.convertToString());
            foodInWeekFragmentItem.setArguments(args);
            return foodInWeekFragmentItem;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null)
                foodInWeekModel = new Gson().fromJson(getArguments().getString(AppUtils.DATA_FOOD_IN_WEEK_ITEM), FoodInWeekModel.class);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.refine_fragment_food_in_week_item, container, false);
            imageView = (ImageView) rootView.findViewById(R.id.refinefragment_food_in_week_item_image);
//            content = (TextView) rootView.findViewById(R.id.fragment_food_in_week_content);
            listItem = (ListView) rootView.findViewById(R.id.refine_fragment_food_in_week_item_listView);
            rootView.post(new Runnable() {
                @Override
                public void run() {
                    fillData();
                }
            });

            return rootView;
        }

        private void fillData() {
//            content.setText(foodInWeekModel.get_content());
            imageView.setImageResource(foodInWeekModel.get_idResource());
            databaseHandler = new DatabaseHandler(getActivity());
            List<FoodsDay> list = databaseHandler.getListFoodsDayWithIdOfDay(foodInWeekModel.get_id());
            FoodInWeekFragmentAdapter foodInWeekFragmentAdapter = new FoodInWeekFragmentAdapter(getActivity(), list);
            listItem.setAdapter(foodInWeekFragmentAdapter);
        }

    }

    public static class AdapterFoodInWeek extends FragmentStatePagerAdapter {
        List<FoodInWeekModel> foodInWeekModelList;

        public AdapterFoodInWeek(FragmentManager fm, List<FoodInWeekModel> list) {
            super(fm);
            this.foodInWeekModelList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return FoodInWeekFragmentItem.newInstance(foodInWeekModelList.get(position));
        }

        @Override
        public int getCount() {
            return foodInWeekModelList.size();
        }
    }

}
