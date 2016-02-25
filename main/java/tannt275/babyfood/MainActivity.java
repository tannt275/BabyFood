package tannt275.babyfood;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tannt275.babyfood.adapter.AdapterMenuDrawer;
import tannt275.babyfood.fragment.AdvicesFragment;
import tannt275.babyfood.fragment.MainFragment;
import tannt275.babyfood.model.MenuModel;

public class MainActivity extends AppCompatActivity implements MainFragment.ButtonListener, AdvicesFragment.ClickItemInFragmentAdvices {

    public static String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listDrawer;
    private Toolbar toolBar;

    private List<MenuModel> menuModelList;
    private AdapterMenuDrawer adapterMenuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        toolBar = (Toolbar) findViewById(R.id.toolbarMain);
        if (toolBar != null){
            setSupportActionBar(toolBar);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.mainLayout);
        listDrawer = (ListView) findViewById(R.id.listDrawer);
        initView();
    }

    private void initView() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_open, R.string.drawer_close)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        menuModelList = generateMenu();
        adapterMenuDrawer = new AdapterMenuDrawer(this, menuModelList);
        listDrawer.setAdapter(adapterMenuDrawer);
        listDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "position: " + position + "data: " + menuModelList.get(position).getTitle());
            }
        });

        MainFragment mainFragment = new MainFragment();
        mainFragment.setButtonListener(this);
        displayFragment(mainFragment, getString(R.string.main_fragment));

    }

    public void displayFragment(Fragment fragment, String tag){
        if (fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.containerMain, fragment, tag);
            fragmentTransaction.commit();
        }
    }

    private List<MenuModel> generateMenu() {
        List<MenuModel> list = new ArrayList<>();
        MenuModel menuModel1 = new MenuModel();
        menuModel1.setId(R.mipmap.ic_launcher);
        menuModel1.setTitle(getString(R.string.drawer_menu_remember));
        list.add(menuModel1);

        MenuModel menuModel2 = new MenuModel();
        menuModel2.setId(R.mipmap.ic_launcher_1);
        menuModel2.setTitle(getString(R.string.drawer_menu_nutrition_tower));
        list.add(menuModel2);
        MenuModel menuModel3 = new MenuModel();
        menuModel3.setId(R.mipmap.ic_launcher);
        menuModel3.setTitle(getString(R.string.drawer_menu_below_one_year));
        list.add(menuModel3);
        MenuModel menuModel4 = new MenuModel();
        menuModel4.setId(R.mipmap.ic_launcher_1);
        menuModel4.setTitle(getString(R.string.drawer_menu_foods));
        list.add(menuModel4);

        return list;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onButtonListener(int idButton) {
        switch (idButton){
            case R.id.main_remember_btn:
                Log.e(TAG, "buttonClick is: "+ idButton);
                AdvicesFragment advicesFragment = new AdvicesFragment();
                advicesFragment.setClickItemInFragmentAdvices(this);
                displayFragment(advicesFragment, getString(R.string.advices_fragment));
                break;
            case R.id.main_remember_nutrition_tower:
                Log.e(TAG, "buttonClick is: "+ idButton);
                break;
            case R.id.main_below_one_year:
                Log.e(TAG, "buttonClick is: "+ idButton);
                break;
            case R.id.main_foods:
                Log.e(TAG, "buttonClick is: "+ idButton);
                break;
        }
    }

    @Override
    public void onClickItemInFragmentAdvices(int pos, String type) {

    }
}
