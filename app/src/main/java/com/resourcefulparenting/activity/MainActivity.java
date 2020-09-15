package com.resourcefulparenting.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.resourcefulparenting.fragment.AboutUsFragment;
import com.resourcefulparenting.fragment.ActivityDetailsFragment;
import com.resourcefulparenting.fragment.ActivityListingFragment;
import com.resourcefulparenting.fragment.ActivityPageFragment;
import com.resourcefulparenting.fragment.ChangePasswordFragment;
import com.resourcefulparenting.fragment.ChangeProfileFragment;
import com.resourcefulparenting.fragment.ExplorerFragment;
import com.resourcefulparenting.fragment.HomeFragment;
import com.resourcefulparenting.fragment.MyAccountGeneralFragment;
import com.resourcefulparenting.R;
import com.resourcefulparenting.fragment.PrivacyPolicyFragment;
import com.resourcefulparenting.fragment.SetParentsNameFragment;
import com.resourcefulparenting.databinding.ActivityMainBinding;
import com.resourcefulparenting.fragment.TermsConditions;
import com.resourcefulparenting.models.Input.MilestoneQuestionCheck;
import com.resourcefulparenting.models.Input.NotificationSend;
import com.resourcefulparenting.models.MilestoneQuestionsResponse;
import com.resourcefulparenting.models.NotificationResponse;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;
import com.resourcefulparenting.util.CheckNetworkConnection;
import com.resourcefulparenting.util.H;
import com.resourcefulparenting.util.Prefs;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements MyAccountGeneralFragment.InsideAccount,
        ActivityListingFragment.activities, ActivityPageFragment.activityPage, ExplorerFragment.InsideExplorer {

    BottomNavigationView bottomNavigation;
    ImageView milestone_img;

    private String name,activity_id,child_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Prefs.setCurrentActivity(this, Prefs.CurrentActivity.MAINACTIVITY);
        H.L("Token"+ Prefs.getLoginToken(this));
        H.L("Child"+ Prefs.getChildDetails(this));

        bottomNavigation = findViewById(R.id.navigation);
        milestone_img = findViewById(R.id.milestone_img);

        milestone_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent  mainIntent = new Intent(MainActivity.this, QuestionsMilestonesActivity.class);
                startActivity(mainIntent);

            }
        });

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

        checkNetWorkDevice();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("child_id", child_id);
        bundle.putString("activity_id", activity_id);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    @Override
    public void onBackPressed() {
        FragmentManager mgr = getSupportFragmentManager();
        Fragment fragment = mgr.findFragmentById(R.id.nav_host_fragment);
        if (bottomNavigation.getSelectedItemId() == R.id.navigation_home)
        {
            mgr.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            super.onBackPressed();
            finish();
        }
        else
        {
            Log.d("Else", "Inside else outside if");
            if(fragment instanceof ExplorerFragment || fragment instanceof ActivityListingFragment
                    || fragment instanceof MyAccountGeneralFragment)
            {
                bottomNavigation.setSelectedItemId(R.id.navigation_home);
                Log.d("Else", "Inside else inside if");
            }else {
                Log.d("Else", "Inside else inside else");
                mgr.popBackStackImmediate();

            }
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
    public void changeProfile(String child_id1) {
        child_id=child_id1;
        loadFragment(new ChangeProfileFragment());
    }

    @Override
    public void privacyPolicy() {
        loadFragment(new PrivacyPolicyFragment());
    }

    @Override
    public void termsConditions() {
        loadFragment(new TermsConditions());
    }

    @Override
    public void aboutUs() {
        loadFragment(new AboutUsFragment());
    }

    @Override
    public void ActivityPage(String activity_id1,String child_id1) {
        activity_id=activity_id1;
        child_id=child_id1;
        loadFragment(new ActivityDetailsFragment());
    }

    @Override
    public void ActivityDetails() {
        loadFragment(new ActivityDetailsFragment());
    }


    private void loadExplorerFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void environment() {
        name = "environment";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void interpersonal() {
        name = "interpersonal";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void intrapersonal() {
        name = "intrapersonal";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void language() {
        name = "language";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void music() {
        name = "music";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void physical() {
        name = "physical";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void logic() {
        name = "logic";
        loadExplorerFragment(new ActivityPageFragment());
    }

    @Override
    public void spatial() {
        name = "spatial";
        loadExplorerFragment(new ActivityPageFragment());
    }
    private void checkNetWorkDevice() {
        if (CheckNetworkConnection.getInstance(this).haveNetworkConnection()) {
            try {
                SendRegistrationToken();
            } catch (Exception e) {
                //e.printStackTrace();();
            }
        }
    }
    private void SendRegistrationToken() {
         NotificationSend activitySendCheck = new NotificationSend();
        activitySendCheck.login_token= Prefs.getLoginToken(this);
        activitySendCheck.device_token=Prefs.getRegistrationTokenID(this);
        activitySendCheck.device_type="Android";
        Call<NotificationResponse> call = ApiClient.getRetrofit().create(Api.class).notificationResponse(activitySendCheck);
        call.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                H.L("responsennnn=" + new Gson().toJson(response.body()));
           //     loading.setVisibility(View.GONE);
               // NotificationResponse response1= response.body();
            /*    if(response1.error.equals("false")){
                    //   H.T(context,response1.message);


                }else {
                    //  H.T(context,response1.message);
                }*/
            }

            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
               // loading.setVisibility(View.GONE);
                //Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}