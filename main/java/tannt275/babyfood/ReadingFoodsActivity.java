package tannt275.babyfood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import tannt275.babyfood.adapter.ReadingFoodsAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.FoodModel;

public class ReadingFoodsActivity extends AppCompatActivity {

    public static String TAG = ReadingFoodsActivity.class.getSimpleName();

    private ImageView addImage;
    private ImageView deleteImage;
    private ImageView favoriteImage;
    private ImageView shareImage;
    private ViewPager viewPager;

    private ReadingFoodsAdapter readingFoodsAdapter;
    private DatabaseHandler databaseHandler;
    private List<FoodModel> foodModelsList;
    private String nameTable;
    private int currentPosition;

    private FoodModel foodModel;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_foods);

        databaseHandler = new DatabaseHandler(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReadingFoods);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            nameTable = bundle.getString(AppUtils.TAG_FOOD_TABLE);
            currentPosition = bundle.getInt(AppUtils.CURRENT_POSITION);
            Log.e(TAG, "nameTable: " + nameTable + " position: " + currentPosition);
        }

        addImage = (ImageView) findViewById(R.id.addFoodImage);
        deleteImage = (ImageView) findViewById(R.id.deleteFoodImage);
        favoriteImage = (ImageView) findViewById(R.id.favoriteFoodImage);
        shareImage = (ImageView) findViewById(R.id.shareFoodImage);
        viewPager = (ViewPager) findViewById(R.id.readingFoodsViewPager);

        fillData();
    }

    private void fillData() {

        foodModelsList = databaseHandler.getFoods(nameTable);
        foodModel = foodModelsList.get(currentPosition);

        readingFoodsAdapter = new ReadingFoodsAdapter(getSupportFragmentManager(), foodModelsList);
        viewPager.setAdapter(readingFoodsAdapter);
        viewPager.setCurrentItem(currentPosition);

        checkStateButton(foodModelsList.get(currentPosition));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                foodModel = foodModelsList.get(position);
                Log.e(TAG, "item when viewpager selected: " + foodModel.convertToString());
                checkStateButton(foodModel);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        databaseHandler.close();

        addImage.setOnClickListener(addItemListener);
        deleteImage.setOnClickListener(deleteItemListener);
        favoriteImage.setOnClickListener(favoriteItemListener);
        shareImage.setOnClickListener(shareItemListener);
    }

    private View.OnClickListener addItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent toAddingActivity = new Intent(ReadingFoodsActivity.this, AddingActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(AppUtils.ADD_TYPE, AppUtils.ADD_FOOD);
            bundle.putString(AppUtils.TAG_FOOD_TABLE, nameTable);
            toAddingActivity.putExtras(bundle);
            startActivity(toAddingActivity);
        }
    };

    private View.OnClickListener deleteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            databaseHandler = new DatabaseHandler(ReadingFoodsActivity.this);
            databaseHandler.deleteObjectFromDataBase(foodModel, nameTable, new DatabaseHandler.SaveDataBase() {
                @Override
                public void saveSuccess() {
                    Toast.makeText(ReadingFoodsActivity.this, getString(R.string.delete_data_success), Toast.LENGTH_SHORT).show();
                    databaseHandler.close();
                }

                @Override
                public void saveFail() {
                    Toast.makeText(ReadingFoodsActivity.this, getString(R.string.delete_data_success), Toast.LENGTH_SHORT).show();
                    databaseHandler.close();
                }
            });
        }
    };
    private View.OnClickListener favoriteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            databaseHandler = new DatabaseHandler(ReadingFoodsActivity.this);
            foodModel.set_favorite(foodModel.get_favorite() == 1 ? 2: 1 );
            databaseHandler.updateFavorite(ReadingFoodsActivity.this, nameTable, foodModel);
            checkStateButton(foodModel);
        }
    };
    private View.OnClickListener shareItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onShareContent(foodModel);
        }
    };

    /**
     * share content to facebook
     *
     * @param food
     */
    private void onShareContent(FoodModel food) {
        Log.e(TAG, "items: " + food.convertToString());

        String linkAppOnStore = "https://play.google.com/store/apps/details?id=" + getPackageName();
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentTitle("Món: " + food.get_nameFood() + " Thời gian: " + food.get_timesFood())
                .setContentDescription(food.get_methodContent())
                .setContentUrl(Uri.parse(linkAppOnStore))
                .build();
        if (shareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    Log.e(TAG, "onSuccess");
                }

                @Override
                public void onCancel() {
                    Log.e(TAG, "onCancel");
                }

                @Override
                public void onError(FacebookException error) {
                    Log.e(TAG, "onError");
                }
            });
            shareDialog.show(shareLinkContent);
        }
    }

    private void checkStateButton(FoodModel foods) {

        deleteImage.setImageResource(foods.get_admins() == 1 ? R.mipmap.app_delete_deactive_icon : R.mipmap.app_delete_active_icon);
        deleteImage.setEnabled(foods.get_admins() == 2);

        favoriteImage.setImageResource(foods.get_favorite() == 1 ? R.mipmap.app_favorite_deactive_icon : R.mipmap.app_favorite_active_icon);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
