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

import tannt275.babyfood.adapter.ReadingAdvicesAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.AdvicesModel;

public class ReadingActivity extends AppCompatActivity {

    public static String TAG = ReadingActivity.class.getSimpleName();
    private ViewPager viewPager;
    private ImageView addImage;
    private ImageView deleteImage;
    private ImageView shareImage;

    private ReadingAdvicesAdapter readingAdvicesAdapter;
    private List<AdvicesModel> advicesModelList;

    private DatabaseHandler databaseHandler;

    private String typeAdvice;
    private int currentPosition;
    private AdvicesModel advicesModel;

    private CallbackManager callbackManager;
    private ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReading);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            typeAdvice = bundle.getString(AppUtils.DATA_TYPE_ADVICES);
            currentPosition = bundle.getInt(AppUtils.CURRENT_POSITION);
            Log.e(TAG, "typeAdvices: " + typeAdvice + " currentPosition: " + currentPosition);
        }

        viewPager = (ViewPager) findViewById(R.id.reading_advicesViewPager);
        addImage = (ImageView) findViewById(R.id.addImage);
        deleteImage = (ImageView) findViewById(R.id.deleteImage);
        shareImage = (ImageView) findViewById(R.id.shareImage);

        fillData();
    }

    private void fillData() {

        advicesModelList = databaseHandler.getListAdvices(typeAdvice);
        readingAdvicesAdapter = new ReadingAdvicesAdapter(getSupportFragmentManager(), advicesModelList);
        viewPager.setAdapter(readingAdvicesAdapter);
        viewPager.setCurrentItem(currentPosition);

        addImage.setOnClickListener(addItemListener);
        deleteImage.setOnClickListener(deleteItemListener);
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
    private View.OnClickListener shareItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            advicesModel = advicesModelList.get(viewPager.getCurrentItem());
            shareContent(advicesModel);
        }
    };

    /**
     *
     * @param advices
     */
    private void shareContent(AdvicesModel advices){

        String linkAppOnStore = "https://play.google.com/store/apps/details?id="+getPackageName();

        Log.e(TAG, "model is: "+ advices.convertToString());

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(advices.get_name())
                .setContentDescription(advices.get_content())
                .setContentUrl(Uri.parse(linkAppOnStore))
                .build();

        if (shareDialog.canShow(ShareLinkContent.class)){
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
            shareDialog.show(linkContent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
