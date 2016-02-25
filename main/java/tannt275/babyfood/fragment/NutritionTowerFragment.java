package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.AdvicesModel;

public class NutritionTowerFragment extends Fragment {

    private ViewPager viewPager;
    private LinearLayout arrowLayout;
    private AdapterNutritionTower adapterNutritionTower;
    private List<AdvicesModel> listNutritionTower;
    private DatabaseHandler databaseHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        listNutritionTower = databaseHandler.getListTowerNutrition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nutrition_tower, container, false);
        arrowLayout = (LinearLayout) rootView.findViewById(R.id.nutrition_tower_arrowlayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.nutrition_tower_viewPager);
        fillData();
        return rootView;
    }

    private void fillData() {

        adapterNutritionTower = new AdapterNutritionTower(getChildFragmentManager(), listNutritionTower);
        viewPager.setAdapter(adapterNutritionTower);
        viewPager.setCurrentItem(0);
        changeArrowInLayout(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeArrowInLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < arrowLayout.getChildCount(); i ++){
            View view = arrowLayout.getChildAt(i);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.setCurrentItem(finalI);
                    changeArrowInLayout(finalI);
                }
            });
        }

    }

    private void changeArrowInLayout(int position) {
        int childCount = arrowLayout.getChildCount();
        if (position > childCount)
            return;
        for (int i = 0; i < childCount; i++) {
            if (i == position)
                arrowLayout.getChildAt(i).setAlpha(1.0f);
            else
                arrowLayout.getChildAt(i).setAlpha(0.3f);
        }
    }

    public static class ItemNutritionTowerFragment extends Fragment {

        private TextView name;
        private TextView content;
        private ImageView imageView;
        private AdvicesModel advicesModel;

        public static ItemNutritionTowerFragment newInstance(AdvicesModel advicesModel) {
            ItemNutritionTowerFragment itemNutritionTowerFragment = new ItemNutritionTowerFragment();
            Bundle args = new Bundle();
            args.putString(AppUtils.DATA_NUTRITION_TOWER_ITEM, advicesModel.convertToString());
            itemNutritionTowerFragment.setArguments(args);
            return itemNutritionTowerFragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null)
                advicesModel = new Gson().fromJson(getArguments().getString(AppUtils.DATA_NUTRITION_TOWER_ITEM), AdvicesModel.class);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.nutrition_tower_fragment_item, container, false);
            content = (TextView) rootView.findViewById(R.id.nutrition_tower_item_content);
            name = (TextView) rootView.findViewById(R.id.nutrition_tower_item_name);
            imageView = (ImageView) rootView.findViewById(R.id.nutrition_tower_item_image);
            fillDataToContent();
            return rootView;
        }

        private void fillDataToContent() {
            name.setText(advicesModel.get_name());
            content.setText(advicesModel.get_content());
            ImageLoader.getInstance().displayImage(advicesModel.get_url(), imageView, AppUtils.OPTION_IMAGE);
        }
    }

    public static class AdapterNutritionTower extends FragmentStatePagerAdapter {
        private List<AdvicesModel> listNutritionTower;

        public AdapterNutritionTower(FragmentManager fm, List<AdvicesModel> listNutritionTower) {
            super(fm);
            this.listNutritionTower = listNutritionTower;
        }

        @Override
        public Fragment getItem(int position) {
            return ItemNutritionTowerFragment.newInstance(listNutritionTower.get(position));
        }

        @Override
        public int getCount() {
            return listNutritionTower.size();
        }
    }
}
