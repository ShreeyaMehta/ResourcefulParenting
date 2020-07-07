package com.resourcefulparenting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class MainActivity extends AppCompatActivity implements MyAccountGeneralFragment.InsideAccount,
        ActivityListingFragment.activities, ActivityPageFragment.activityPage {

    MeowBottomNavigation bottomNavigation;
    private final static int Id_Home = 1;
    private final static int Id_Explorer = 2;
    private final static int Id_Activity = 3;
    private final static int Id_Account = 4;
    private String login_token, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_token = getIntent().getStringExtra("access_token");

        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_explorer));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_activity));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.myaccount_icon));

        bottomNavigation.setOnClickMenuListener(item -> Log.d("Show", String.valueOf(item.getId())));

        bottomNavigation.setOnShowListener(item -> {
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
        bottomNavigation.show(Id_Home, true);
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