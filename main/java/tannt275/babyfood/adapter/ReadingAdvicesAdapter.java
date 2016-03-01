package tannt275.babyfood.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import tannt275.babyfood.fragment.ReadingAdvicesFragment;
import tannt275.babyfood.model.AdvicesModel;

/**
 * Created by tannt on 3/1/2016.
 */
public class ReadingAdvicesAdapter extends FragmentStatePagerAdapter {
    private List<AdvicesModel> advicesModelList;

    public ReadingAdvicesAdapter(FragmentManager fm, List<AdvicesModel> advicesModelList) {
        super(fm);
        this.advicesModelList = advicesModelList;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadingAdvicesFragment.newInstance(advicesModelList.get(position));
    }

    @Override
    public int getCount() {
        return advicesModelList.size();
    }
}
