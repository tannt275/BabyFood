package tannt275.babyfood.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import tannt275.babyfood.fragment.ReadingFoodsFragment;
import tannt275.babyfood.model.FoodModel;

/**
 * Created by tannt on 3/1/2016.
 */
public class ReadingFoodsAdapter extends FragmentStatePagerAdapter {

    private List<FoodModel> foodModels;
    public ReadingFoodsAdapter(FragmentManager fm, List<FoodModel> foodModels) {
        super(fm);
        this.foodModels = foodModels;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadingFoodsFragment.newInstance(foodModels.get(position));
    }

    @Override
    public int getCount() {
        return foodModels.size();
    }
}
