package tannt275.babyfood.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.fragment.AdvicesFragment;
import tannt275.babyfood.fragment.AdvicesFragmentBase;

/**
 * Created by TanNT on 12/18/2015.
 */
public class AdapterAdvices extends FragmentStatePagerAdapter implements AdvicesFragmentBase.ClickItemInListAdvices {

    private static int MAX_SIZE = 2;
    private ClickAdapterAdvices clickAdapterAdvices;

    public void setClickAdapterAdvices(ClickAdapterAdvices clickAdapterAdvices) {
        this.clickAdapterAdvices = clickAdapterAdvices;
    }

    public AdapterAdvices(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                AdvicesFragmentBase advicesFragmentBaseRemember = AdvicesFragmentBase.newInstance(AppUtils.TAG_REMEMBER);
                advicesFragmentBaseRemember.setClickItemInListAdvices(this);
                return advicesFragmentBaseRemember;
            case 1:
                AdvicesFragmentBase advicesFragmentBaseForgot = AdvicesFragmentBase.newInstance(AppUtils.TAG_FORGET);
                advicesFragmentBaseForgot.setClickItemInListAdvices(this);
                return advicesFragmentBaseForgot;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return MAX_SIZE;
    }

    @Override
    public void onClickItemInListAdvices(int posClick, String type) {
        clickAdapterAdvices.onClickAdapterAdvices(posClick, type);
    }

    public interface ClickAdapterAdvices{
        public void onClickAdapterAdvices(int pos, String type);
    }
}
