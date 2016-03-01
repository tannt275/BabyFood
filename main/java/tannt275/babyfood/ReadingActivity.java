package tannt275.babyfood;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tannt275.babyfood.adapter.ReadingAdvicesAdapter;

public class ReadingActivity extends AppCompatActivity {

    public static String TAG = ReadingActivity.class.getSimpleName();
    private ViewPager viewPager;
    private ReadingAdvicesAdapter readingAdvicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReading);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        viewPager = (ViewPager) findViewById(R.id.reading_advicesViewPager);
        readingAdvicesAdapter = new ReadingAdvicesAdapter(getSupportFragmentManager());
        viewPager.setAdapter(readingAdvicesAdapter);
    }

}
