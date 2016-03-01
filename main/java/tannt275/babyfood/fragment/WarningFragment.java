package tannt275.babyfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.ReadingActivity;
import tannt275.babyfood.adapter.AdvicesAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.AdvicesModel;

public class WarningFragment extends Fragment implements AdvicesAdapter.ClickItemAdvices {

    public static String TAG = WarningFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private DatabaseHandler databaseHandler;
    private List<AdvicesModel> waringLists;
    private AdvicesAdapter advicesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        waringLists = databaseHandler.getListAdvices(AppUtils.TAG_FORGET);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_advices, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.advicesRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        return rootView;
    }

    private void initData() {
        advicesAdapter = new AdvicesAdapter(getActivity(), waringLists);
        advicesAdapter.setClickItemAdvices(this);
        recyclerView.setAdapter(advicesAdapter);
    }

    @Override
    public void onClickItem(AdvicesModel advicesModel) {
        Log.e(TAG, " position: " + waringLists.indexOf(advicesModel) + " Item: " + advicesModel.convertToString());
        Intent toReadingActivity = new Intent(getActivity(), ReadingActivity.class);

        int currentPosition = waringLists.indexOf(advicesModel);
        Bundle bundle = new Bundle();
        bundle.putInt(AppUtils.CURRENT_POSITION, currentPosition);
        bundle.putString(AppUtils.DATA_TYPE_ADVICES, AppUtils.TAG_FORGET);
        toReadingActivity.putExtras(bundle);
        startActivity(toReadingActivity);
    }
}
