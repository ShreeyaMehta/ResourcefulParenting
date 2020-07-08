package com.resourcefulparenting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements MyAccountGeneralFragment.InsideAccount,
        ActivityListingFragment.activities, ActivityPageFragment.activityPage {

    //MeowBottomNavigation bottomNavigation;
    BottomNavigationView bottomNavigation;
    private final static int Id_Home = 1;
    private final static int Id_Explorer = 2;
    private final static int Id_Activity = 3;
    private final static int Id_Account = 4;
    private String login_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_token = getIntent().getStringExtra("access_token");

        bottomNavigation = findViewById(R.id.navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    bottomNavigation.setItemIconTintList(null);
                    loadFragment(new HomeFragment());
                    return true;
                case R.id.navigation_explorer:
                    bottomNavigation.setItemIconTintList(null);
                    loadFragment(new ExplorerFragment());
                    return true;
                case R.id.navigation_activity:
                    bottomNavigation.setItemIconTintList(null);
                    loadFragment(new ActivityListingFragment());
                    return true;
                case R.id.navigation_account:
                    bottomNavigation.setItemIconTintList(null);
                    loadFragment(new MyAccountGeneralFragment());
                    return true;
            }
            return false;
        });

        bottomNavigation.setSelectedItemId(R.id.navigation_home);

       /* bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_explorer));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_activity));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.myaccount_icon));

        bottomNavigation.setOnClickMenuListener(item -> Log.d("Show", String.valueOf(item.getId())));

        bottomNavigation.setOnShowListener(item -> {
            Log.d("MainActivity", String.valueOf(item));

            switch (item.getId()){
                case Id_Home:
                    loadFragment(new HomeFragment());
                    bottomNavigation.setSelected(true);
                    break;
                case Id_Explorer:
                    loadFragment(new ExplorerFragment());
                    break;
                case Id_Activity:
                    loadFragment(new ActivityListingFragment());
                    break;
                case Id_Account:
                    loadFragment(new MyAccountGeneralFragment());
                    break;
            }
        });

      /*  bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

            }
        });*/


       /* bottomNavigation.show(Id_Home, true);

        bottomNavigation.setOnReselectListener(item -> {
            switch (item.getId()){
                case Id_Home:
                    loadFragment(new HomeFragment());
                    bottomNavigation.setSelected(true);
                    break;
                case Id_Explorer:
                    loadFragment(new ExplorerFragment());
                    break;
                case Id_Activity:
                    loadFragment(new ActivityListingFragment());
                    break;
                case Id_Account:
                    loadFragment(new MyAccountGeneralFragment());
                    break;
            }
        });*/
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("access_token", login_token);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager mgr = getSupportFragmentManager();
        if (mgr.getBackStackEntryCount() == 1) {
            // No backstack to pop, so calling super
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setName() {
        loadFragment(new SetParentsNameFragment());
    }

    @Override
    public void changePassword() {
        loadFragment(new ChangePasswordFragment());
    }

    @Override
    public void changeProfile() {
        loadFragment(new ChangeProfileFragment());
    }

    @Override
    public void ActivityPage() {
        loadFragment(new ActivityPageFragment());
    }

    @Override
    public void ActivityDetails() {
        loadFragment(new ActivityDetailsFragment());
    }
}