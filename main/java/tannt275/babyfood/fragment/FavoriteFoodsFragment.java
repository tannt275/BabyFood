package tannt275.babyfood.fragment;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tannt275.babyfood.FavoriteActivity;
import tannt275.babyfood.R;
import tannt275.babyfood.adapter.FoodsAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.FoodModel;

/**
 * Created by tannt on 3/8/2016.
 */
public class FavoriteFoodsFragment extends Fragment implements FoodsAdapter.ClickItemFoods {

    private String TAG = FavoriteFoodsFragment.class.getSimpleName();

    private DatabaseHandler databaseHandler;
    private TextView emptyFavorite;
    private RecyclerView recyclerView;
    private List<FoodModel> favoriteList;
    private FoodsAdapter foodsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getActivity());
        favoriteList = databaseHandler.getListFavorite();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite_foods_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favorite_foodsRecyclerView);
        emptyFavorite = (TextView) rootView.findViewById(R.id.favorite_empty);
        emptyFavorite.setVisibility(View.GONE);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        initData();
        return rootView;
    }

    private void initData() {
        emptyFavorite.setVisibility(favoriteList.size() == 0 ? View.VISIBLE : View.GONE);
        foodsAdapter = new FoodsAdapter(getActivity(), favoriteList);
        foodsAdapter.setClickItemFoods(this);
        recyclerView.setAdapter(foodsAdapter);
    }

    @Override
    public void onClickItemFoods(FoodModel foodModel) {
        Intent toFavoriteActivity = new Intent(getActivity(), FavoriteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(AppUtils.CURRENT_POSITION, favoriteList.indexOf(foodModel));
        toFavoriteActivity.putExtras(bundle);
        startActivity(toFavoriteActivity);
    }
}
