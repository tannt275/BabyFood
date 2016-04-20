package tannt275.babyfood;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.fragment.FavoriteFoodsFragment;
import tannt275.babyfood.fragment.FoodInWeekFragment;
import tannt275.babyfood.fragment.FoodsFragment;
import tannt275.babyfood.fragment.NutritionTowerFragment;
import tannt275.babyfood.fragment.ShouldFragment;
import tannt275.babyfood.fragment.WarningFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String TAG = MainActivity.class.getSimpleName();

    private AdView mAdView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refine_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdView = (AdView) findViewById(R.id.main_bottom_adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        NutritionTowerFragment nutritionTowerFragment = new NutritionTowerFragment();
        displayFragment(nutritionTowerFragment);

    }

    private void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.refine_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_nutrition:
                NutritionTowerFragment nutritionTowerFragment = new NutritionTowerFragment();
                displayFragment(nutritionTowerFragment);
                break;
            case R.id.nav_food_in_week:
                FoodInWeekFragment foodInWeekFragment = new FoodInWeekFragment();
                displayFragment(foodInWeekFragment);
                break;
            case R.id.nav_remember:
                ShouldFragment shouldFragment = new ShouldFragment();
                displayFragment(shouldFragment);
                break;
            case R.id.nav_waring:
                WarningFragment warningFragment = new WarningFragment();
                displayFragment(warningFragment);
                break;
            case R.id.nav_below_8_months:
                FoodsFragment foodsFragment_below_8_months = FoodsFragment.newInstance(AppUtils.TAG_8MONTHS);
                displayFragment(foodsFragment_below_8_months);
                break;
            case R.id.nav_9_to_11_months:
                FoodsFragment foodsFragment_9_11_months = FoodsFragment.newInstance(AppUtils.TAG_9MONTHS);
                displayFragment(foodsFragment_9_11_months);
                break;
            case R.id.nav_below_15_months:
                FoodsFragment foodsFragment_below_15_months = FoodsFragment.newInstance(AppUtils.TAG_15MONTHS);
                displayFragment(foodsFragment_below_15_months);
                break;
            case R.id.nav_favorite:
                FavoriteFoodsFragment favoriteFoodsFragment = new FavoriteFoodsFragment();
                displayFragment(favoriteFoodsFragment);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
