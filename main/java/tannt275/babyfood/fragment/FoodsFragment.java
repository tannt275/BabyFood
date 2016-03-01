package tannt275.babyfood.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tannt275.babyfood.R;
import tannt275.babyfood.adapter.FoodsAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.FoodModel;

public class FoodsFragment extends Fragment implements FoodsAdapter.ClickItemFoods {

    public static String TAG = FoodsFragment.class.getSimpleName();

    private DatabaseHandler databaseHandler;
    private RecyclerView recyclerView;
    private List<FoodModel> foodModels;
    private FoodsAdapter foodsAdapter;

    private String nameTable;

    public static FoodsFragment newInstance(String nameTable){
        FoodsFragment foodsFragment = new FoodsFragment();
        Bundle args = new Bundle();
        args.putString(AppUtils.TAG_FOOD_TABLE, nameTable);
        foodsFragment.setArguments(args);
        return foodsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            nameTable = getArguments().getString(AppUtils.TAG_FOOD_TABLE);

        databaseHandler = new DatabaseHandler(getActivity());
        foodModels = databaseHandler.getFoods(nameTable);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_foods, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_foodsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        initData();
        return rootView;
    }

    private void initData() {
        foodsAdapter = new FoodsAdapter(getActivity(), foodModels);
        foodsAdapter.setClickItemFoods(this);
        recyclerView.setAdapter(foodsAdapter);
    }

    @Override
    public void onClickItemFoods(FoodModel foodModel) {
        Log.e(TAG, "item: " + foodModel.convertToString());
    }
}
