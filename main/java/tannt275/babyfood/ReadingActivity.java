package tannt275.babyfood;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.List;

import tannt275.babyfood.adapter.ReadingAdvicesAdapter;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.AdvicesModel;

public class ReadingActivity extends AppCompatActivity {

    public static String TAG = ReadingActivity.class.getSimpleName();
    private ViewPager viewPager;
    private ReadingAdvicesAdapter readingAdvicesAdapter;
    private List<AdvicesModel> advicesModelList;

    private DatabaseHandler databaseHandler;

    private String typeAdvice;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReading);
        setSupportActionBar(toolbar);

        databaseHandler = new DatabaseHandler(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            typeAdvice = bundle.getString(AppUtils.DATA_TYPE_ADVICES);
            currentPosition = bundle.getInt(AppUtils.CURRENT_POSITION);
            Log.e(TAG, "typeAdvices: " + typeAdvice + " currentPosition: " + currentPosition);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewPager = (ViewPager) findViewById(R.id.reading_advicesViewPager);

        fillData();
    }

    private void fillData() {
        advicesModelList = databaseHandler.getListAdvices(typeAdvice);
        readingAdvicesAdapter = new ReadingAdvicesAdapter(getSupportFragmentManager(), advicesModelList);
        viewPager.setAdapter(readingAdvicesAdapter);
        viewPager.setCurrentItem(currentPosition);
    }

}
