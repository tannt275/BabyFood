package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import tannt275.babyfood.R;
import tannt275.babyfood.adapter.AdapterAdvices;

/**
 * Created by TanNT on 12/18/2015.
 */
public class AdvicesFragment extends Fragment implements AdapterAdvices.ClickAdapterAdvices {

    public static String TAG = AdvicesFragment.class.getSimpleName();

    private ViewPager viewPager;
    private RelativeLayout shouldHeaderLayout;
    private RelativeLayout forgetHeaderLayout;
    private View shouldViewIndicator;
    private View forgetViewIndicator;

    private ClickItemInFragmentAdvices clickItemInFragmentAdvices;

    public void setClickItemInFragmentAdvices(ClickItemInFragmentAdvices clickItemInFragmentAdvices) {
        this.clickItemInFragmentAdvices = clickItemInFragmentAdvices;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.advices_fragment, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.advicesViewPager);
        shouldHeaderLayout = (RelativeLayout) rootView.findViewById(R.id.shouldLayout);
        forgetHeaderLayout = (RelativeLayout) rootView.findViewById(R.id.forgetLayout);
        shouldViewIndicator = rootView.findViewById(R.id.indicatorShould);
        forgetViewIndicator = rootView.findViewById(R.id.indicatorForget);
        AdapterAdvices adapterAdvices = new AdapterAdvices(getChildFragmentManager());
        adapterAdvices.setClickAdapterAdvices(this);
        viewPager.setAdapter(adapterAdvices);
        setCurrentLayout(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setCurrentLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        shouldHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentLayout(0);
            }
        });
        forgetHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentLayout(1);
            }
        });

        return rootView;
    }

    private void setCurrentLayout(int position){
        viewPager.setCurrentItem(position);
        switch (position){
            case 0:
                shouldHeaderLayout.setAlpha(1.0f);
                shouldViewIndicator.setAlpha(1.0f);
                forgetViewIndicator.setAlpha(0.3f);
                forgetHeaderLayout.setAlpha(0.3f);
                break;
            case 1:
                shouldHeaderLayout.setAlpha(0.3f);
                shouldViewIndicator.setAlpha(0.3f);
                forgetHeaderLayout.setAlpha(1.0f);
                forgetViewIndicator.setAlpha(1.0f);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickAdapterAdvices(int pos, String type) {
        clickItemInFragmentAdvices.onClickItemInFragmentAdvices(pos, type);
    }

    public interface ClickItemInFragmentAdvices{
        public void onClickItemInFragmentAdvices(int pos, String type);
    }
}
