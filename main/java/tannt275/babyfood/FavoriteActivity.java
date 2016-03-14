package tannt275.babyfood;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import tannt275.babyfood.adapter.ReadingFoodsAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.FoodModel;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = FavoriteActivity.class.getSimpleName();

    private Toolbar toolbar;

    private ViewPager viewPager;
    private ImageView deleteIcon;
    private ImageView addIcon;
    private ImageView editIcon;
    private ImageView shareIcon;

    private int currentPosition;
    private ReadingFoodsAdapter favoriteFoodsAdapter;
    private DatabaseHandler databaseHandler;
    private List<FoodModel> favoriteFoodsList;

    private FoodModel foodModel;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        databaseHandler = new DatabaseHandler(this);

        toolbar = (Toolbar) findViewById(R.id.toolbarFavoriteFoods);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            currentPosition = bundle.getInt(AppUtils.CURRENT_POSITION);

        viewPager = (ViewPager) findViewById(R.id.favorite_viewPager);
        deleteIcon = (ImageView) findViewById(R.id.deleteImage);
        addIcon = (ImageView) findViewById(R.id.addImage);
        editIcon = (ImageView) findViewById(R.id.editImage);
        shareIcon = (ImageView) findViewById(R.id.shareImage);

        deleteIcon.setOnClickListener(this);
        addIcon.setOnClickListener(this);
        editIcon.setOnClickListener(this);
        shareIcon.setOnClickListener(this);

        fillData();
    }

    private void fillData() {

        favoriteFoodsList = databaseHandler.getListFavorite();
        foodModel = favoriteFoodsList.get(currentPosition);
        checkStateButton(foodModel);

        favoriteFoodsAdapter = new ReadingFoodsAdapter(getSupportFragmentManager(), favoriteFoodsList);
        viewPager.setAdapter(favoriteFoodsAdapter);
        viewPager.setCurrentItem(currentPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                foodModel = favoriteFoodsList.get(position);
                checkStateButton(foodModel);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void checkStateButton(FoodModel foods) {

        deleteIcon.setImageResource(foods.get_admins() == 1 ? R.mipmap.app_delete_deactive_icon : R.mipmap.app_delete_active_icon);
        deleteIcon.setEnabled(foods.get_admins() == 2);


        editIcon.setImageResource(foods.get_admins() == 1 ? R.mipmap.app_edit_deactive_icon : R.mipmap.app_edit_active_icon);
        editIcon.setEnabled(foods.get_admins() == 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addImage:
                break;
            case R.id.deleteImage:
                break;
            case R.id.editImage:
                break;
            case R.id.shareImage:
                break;
        }
    }
}
