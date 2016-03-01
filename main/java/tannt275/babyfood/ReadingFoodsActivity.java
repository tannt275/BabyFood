package tannt275.babyfood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

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
    private List<FoodModel> foodModels;
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

        foodModels = databaseHandler.getFoods(nameTable);
        readingFoodsAdapter = new ReadingFoodsAdapter(getSupportFragmentManager(), foodModels);
        viewPager.setAdapter(readingFoodsAdapter);
        viewPager.setCurrentItem(currentPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                foodModel = foodModels.get(position);
                checkStateButton(foodModel);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addImage.setOnClickListener(addItemListener);
        deleteImage.setOnClickListener(deleteItemListener);
        favoriteImage.setOnClickListener(favoriteItemListener);
        shareImage.setOnClickListener(shareItemListener);
    }

    private View.OnClickListener addItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener deleteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener favoriteItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private View.OnClickListener shareItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String linkAppOnStore = "https://play.google.com/store/apps/details?id=" + getPackageName();
            ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Món: " + foodModel.get_nameFood() + " Thời gian: " + foodModel.get_timesFood())
                    .setContentDescription(foodModel.get_methodContent())
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
    };

    private void checkStateButton(FoodModel foods) {
        deleteImage.setEnabled(foods.get_admins() == 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
