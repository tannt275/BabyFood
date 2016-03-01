package tannt275.babyfood;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.facebook.appevents.AppEventsLogger;

import java.awt.font.TextAttribute;

import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.FileUtils;
import tannt275.babyfood.common.MySharePref;

public class SplashActivity extends AppCompatActivity {

    private int TIME_DELAY = 2*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String str = AppUtils.getKeyHashFacebook(this);
        Log.e("Splash", str);
        checkAndSaveDataBase();
    }

    private void checkAndSaveDataBase() {
        if (MySharePref.get(AppUtils.IS_FIRST_USE, true)){
            FileUtils.saveFileDataBase(SplashActivity.this, new FileUtils.OnSaveDataSuccess() {
                @Override
                public void saveSuceess() {
                    jumpToMain();
                }
            });
        } else {
            jumpToMain();
        }
    }

    private void jumpToMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent toMain = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(toMain);
                finish();
            }
        },TIME_DELAY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }
}
