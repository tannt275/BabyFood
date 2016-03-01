package tannt275.babyfood.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tannt275.babyfood.fragment.ReadingAdvicesFragment;

/**
 * Created by tannt on 3/1/2016.
 */
public class ReadingAdvicesAdapter extends FragmentStatePagerAdapter {

    public ReadingAdvicesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ReadingAdvicesFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
