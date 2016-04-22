package tannt275.babyfood;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
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
    private DrawerLayout drawerLayout;

    private int indexItemSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refine_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdView = (AdView) findViewById(R.id.main_bottom_adView);
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        View headerLayout =  navigationView.getHeaderView(0);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.closeDrawer(GravityCompat.START);
                showDialogAction();
            }
        });

        NutritionTowerFragment nutritionTowerFragment = new NutritionTowerFragment();
        displayFragment(nutritionTowerFragment, getString(R.string.drawer_menu_nutrition_tower));
    }

    private void displayFragment(Fragment fragment, String tagFragment) {
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(tagFragment);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment, tagFragment);
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
                displayFragment(nutritionTowerFragment,getString(R.string.drawer_menu_nutrition_tower));
                break;
            case R.id.nav_food_in_week:
                FoodInWeekFragment foodInWeekFragment = new FoodInWeekFragment();
                displayFragment(foodInWeekFragment, getString(R.string.drawer_food_in_week));
                break;
            case R.id.nav_remember:
                ShouldFragment shouldFragment = new ShouldFragment();
                displayFragment(shouldFragment, getString(R.string.drawer_should));
                break;
            case R.id.nav_waring:
                WarningFragment warningFragment = new WarningFragment();
                displayFragment(warningFragment, getString(R.string.drawer_warning));
                break;
            case R.id.nav_below_8_months:
                FoodsFragment foodsFragment_below_8_months = FoodsFragment.newInstance(AppUtils.TAG_8MONTHS);
                displayFragment(foodsFragment_below_8_months, getString(R.string.drawer_foods_below_8_months));
                break;
            case R.id.nav_9_to_11_months:
                FoodsFragment foodsFragment_9_11_months = FoodsFragment.newInstance(AppUtils.TAG_9MONTHS);
                displayFragment(foodsFragment_9_11_months, getString(R.string.drawer_foods_9_11_months));
                break;
            case R.id.nav_below_15_months:
                FoodsFragment foodsFragment_below_15_months = FoodsFragment.newInstance(AppUtils.TAG_15MONTHS);
                displayFragment(foodsFragment_below_15_months, getString(R.string.drawer_foods_15_months));
                break;
            case R.id.nav_favorite:
                FavoriteFoodsFragment favoriteFoodsFragment = new FavoriteFoodsFragment();
                displayFragment(favoriteFoodsFragment, getString(R.string.drawer_foods_favorite));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDialogAction() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        final String [] items = new String []{"Fedback","Rating","Apps"};
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle("Choose an action");
        alertDialogBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                indexItemSelected = which;
            }
        });
        alertDialogBuilder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chooseAction(indexItemSelected);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }

    private void chooseAction( int indexItemSelected) {
        Log.e(TAG, "index of item selected: " + indexItemSelected);
        switch (indexItemSelected){
            case 0:
                sendEmailToMe();
                break;
            case 1:
                ratingAppsOnStore();
                break;
            case 2:
                break;
        }
    }

    private void ratingAppsOnStore() {

        Uri uri = Uri.parse("market://details?id=" + MainActivity.this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
        }
    }

    private void sendEmailToMe() {
        String[] TO = {"tannt275@gmail.com"};
        String[] CC = {"tannt.hanoi@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.e("Finished sending email...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
