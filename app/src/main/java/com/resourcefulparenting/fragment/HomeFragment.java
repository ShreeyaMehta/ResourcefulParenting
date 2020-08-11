package com.resourcefulparenting.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.resourcefulparenting.R;
import com.resourcefulparenting.activity.AddChildName;

import com.resourcefulparenting.activity.MainActivity;
import com.resourcefulparenting.activity.QuestionsMilestonesActivity;
import com.resourcefulparenting.adapter.AdapterActivityListing;
import com.resourcefulparenting.adapter.AdapterImageListing;
import com.resourcefulparenting.databinding.FragmentHomeBinding;
import com.resourcefulparenting.models.AcyivitySendComResponse;
import com.resourcefulparenting.models.AddChild.AddChildCheck;
import com.resourcefulparenting.models.AddChild.ChildDetails;
import com.resourcefulparenting.models.AddChild.GarphpointDetails;
import com.resourcefulparenting.models.AlarmResponse;
import com.resourcefulparenting.models.CommonResponse;
import com.resourcefulparenting.models.Input.ActivitySendCheck;
import com.resourcefulparenting.models.Input.AlarmCheck;
import com.resourcefulparenting.models.Input.ChildeImageCheck;
import com.resourcefulparenting.models.Input.TodaysactivityCheck;
import com.resourcefulparenting.models.Input.UpdatechildCheck;
import com.resourcefulparenting.models.Input.VideoCheck;
import com.resourcefulparenting.models.ProfileImageResponse;
import com.resourcefulparenting.models.QueriesResponse;
import com.resourcefulparenting.models.TodayAcyivityResponse;
import com.resourcefulparenting.models.Userdetails;
import com.resourcefulparenting.models.VideoResponse;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;
import com.resourcefulparenting.util.CheckNetworkConnection;
import com.resourcefulparenting.util.CheckPermission;
import com.resourcefulparenting.util.H;
import com.resourcefulparenting.util.Logout;
import com.resourcefulparenting.util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.FileProvider.getUriForFile;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    String activity_id;
    PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    Context context;
    ArrayList<PieEntry> pieEntries;
    private Spinner child;
    private ConstraintLayout target_Activity, show_activity;
    private boolean isvisible = false;
    Float point;
    final int FROM_GALLERY = 100;
    final int FROM_CAMERA = 200;
    String img_base64="";
    Dialog dialog;
    private CheckPermission checkPermission;
    private List<ChildDetails> childDetails1 = new ArrayList<>();
    private List<GarphpointDetails> garphpointDetails1 = new ArrayList<>();

    final TodaysactivityCheck todaysactivityCheck = new TodaysactivityCheck();
    final ActivitySendCheck activitySendCheck = new ActivitySendCheck();
        final AlarmCheck alarmCheck = new AlarmCheck();
    Boolean isalarmset;
    ArrayList<String> childs = new ArrayList<>();
    String child_id="";

    final ChildeImageCheck childeImageCheck = new ChildeImageCheck();
    final VideoCheck videoCheck = new VideoCheck();
    AdapterImageListing adapterImageListing;
    TodayAcyivityResponse todayAcyivityResponse;
    ArrayList<String> images = new ArrayList<>();
    AsyncTask mMyTask;
    ProgressDialog mProgressDialog;
    String iconsStoragePath = Environment.getExternalStorageDirectory() + "/Parenting/";
    URL url1;
    URL url2;
     URL url;
    public HomeFragment() {
        // Required empty public constructor
    }
  /*  private final URL[] URLS = {stringToURL("https://resourcefulparenting.parkmapped.com/uploads/childactivityimg/130-4-1596190217.png"),
            stringToURL("https://resourcefulparenting.parkmapped.com/uploads/childactivityimg/130-4-1596180332.png")

    };*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        pieChart = binding.pieChart;
        child = binding.spinChildName;
        target_Activity = binding.showHideActivityDetails;
        show_activity = binding.homeActivity;
        context = container.getContext();
     //   login_token = getArguments().getString("login_token");
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkPermission = new CheckPermission(getActivity());
        ImageView milestone =getActivity().findViewById(R.id.milestone_img);
        milestone.setVisibility(View.VISIBLE);


       // mImageView = findViewById(R.id.imageView);
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("Parenting");
        mProgressDialog.setMessage("Please wait, we are downloading your image file...");
       // getEntries();
      binding.btnMilestone.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent  mainIntent = new Intent(context, QuestionsMilestonesActivity.class);
              startActivity(mainIntent);
          }
      });

        binding.addChild.setOnClickListener(view12 -> {
            Intent add_child = new Intent(context, AddChildName.class);
            startActivity(add_child);
        });

        binding.homeShare.setOnClickListener(view12 -> {

           // mMyTask = new DownloadTask().execute(url1,url2);
            if (!images.isEmpty())
            {

                if (images.size() == 2)
                {
                    url1 = stringToURL(images.get(0));
                    url2 = stringToURL(images.get(1));
                    mMyTask = new DownloadTask().execute(url1,url2);
                }
                else {
                    url1 = stringToURL(images.get(0));
                    mMyTask = new DownloadTask().execute(url1);

                }

            }
        });

        binding.btnRegister.setOnClickListener(view12 -> {
              checkNetWorkComplit();
        });


        if (images.size() > 3)
        {
            binding.homeCamera.setVisibility(View.GONE);
        }
        else {
            binding.homeCamera.setVisibility(View.VISIBLE);
        }
        binding.homeCamera.setOnClickListener(view12 -> {

            if (images.size() > 3)
            {
                H.T(context,"Image Already Uploaded");
            }
            else {
                showCaptureDialog();
            }
            H.L("size;;;"+images.size());
        });
        binding.homeVideo.setOnClickListener(view12 -> {
          //  showVideoi();
            showpopupTitleDeedUpload();
        });
        binding.homeAlarm.setOnClickListener(view12 -> {
            checkNetWorkAlarm();
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        binding.rvList.setLayoutManager(mLayoutManager);
        try {
            childDetails1.clear();
            JSONArray jsonArray=new JSONArray(Prefs.getChildDetails(context));
          //  Log.d("Arraym", String.valueOf(jsonArray.length()));
            for (int i=0;i<jsonArray.length();i++)
            {
                ChildDetails childDetails=new ChildDetails();
                JSONObject object = jsonArray.getJSONObject(i);
                String id=object.getString("child_id");
                String name=object.getString("child_name");
                childDetails.setId(id);
                childDetails.setChild_name(name);
                childs.add(name);
                childDetails1.add(childDetails);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, R.layout.spinner_text, childs);
        // attaching data adapter to spinner
        child.setAdapter(dataAdapter);

        child.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String text = parent.getItemAtPosition(pos).toString();

                for (int j = 0; j < childDetails1.size(); j++) {
                    if (text.equalsIgnoreCase(childDetails1.get(j).getChild_name()))
                    {
                        child_id = childDetails1.get(j).getId();
                        H.L("idd"+childDetails1.get(j).getId());
                        checkNetWork();
                        Prefs.setChildID(context, child_id);
                        break;

                    }
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        target_Activity.setOnClickListener(view1 -> {
            if(!isvisible){
                show_activity.setVisibility(View.VISIBLE);
                isvisible = true;
            }else {
                show_activity.setVisibility(View.GONE);
                isvisible = false;
            }
        });
    }



    private void checkNetWork() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                getTodayActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }

    private void getTodayActivity() {
        images.clear();
       binding. loading.setVisibility(View.VISIBLE);
        todaysactivityCheck.login_token= Prefs.getLoginToken(getActivity());
        todaysactivityCheck.child_id= child_id;
        Call<TodayAcyivityResponse> call = ApiClient.getRetrofit().create(Api.class).getTodayActivity(todaysactivityCheck);
        call.enqueue(new Callback<TodayAcyivityResponse>() {
            @Override
            public void onResponse(Call <TodayAcyivityResponse> call, Response<TodayAcyivityResponse> response) {
               binding.loading.setVisibility(View.GONE);
                H.L("responsennnn=" + new Gson().toJson(response.body()));
                 todayAcyivityResponse=response.body();
                if (todayAcyivityResponse !=null && response.code() == 200)
                {
                    if (todayAcyivityResponse.error.equals("false"))
                      {
                          binding.numOfActivityCompleted.setText(todayAcyivityResponse.total_activities_completed);
                        //  binding.numOfPointsEarned.setText(response1.badges);
                          binding.numOfPointsEarned.setText(todayAcyivityResponse.total_points);
                          if (todayAcyivityResponse.activitiesDetails !=null)
                          {
                              binding.tvTargetActivity.setText(todayAcyivityResponse.activitiesDetails.category_name);
                              binding.tvActivityName.setText(todayAcyivityResponse.activitiesDetails.activity_name);
                              binding.homeEdtDescription.setText(Html.fromHtml(todayAcyivityResponse.activitiesDetails.activity_description));
                              binding.edtLearning.setText(Html.fromHtml(todayAcyivityResponse.activitiesDetails.activity_learning));

                              if (todayAcyivityResponse.activitiesDetails.iscompleted)
                              {
                                  binding.btnRegister.setText(getResources().getString(R.string.do_it_again));
                              }
                              else {
                                  binding.btnRegister.setText(getResources().getString(R.string.we_did_it));
                              }
                              activity_id=todayAcyivityResponse.activitiesDetails.activity_id;
                              images.addAll(todayAcyivityResponse.activities_imgs);
                              if (todayAcyivityResponse.activitiesDetails.isalarmset)
                              {
                                  isalarmset=false;
                                  binding.homeAlarm.setImageDrawable(getResources().getDrawable(R.drawable.alarm_off));
                              }
                              else
                              {
                                  isalarmset=true;
                                  binding.homeAlarm.setImageDrawable(getResources().getDrawable(R.drawable.alarm));
                              }

                              if (todayAcyivityResponse.milestone)
                              {
                                  binding.milestoneLayout.setVisibility(View.INVISIBLE);
                              }
                              else {
                                  binding.milestoneLayout.setVisibility(View.VISIBLE);
                              }

                          }

                          garphpointDetails1.clear();


                          if (todayAcyivityResponse.activities_imgs.size()>0)
                          {
                              adapterImageListing = new AdapterImageListing(context,images,binding.homeCamera);
                              binding.rvList.setAdapter(adapterImageListing);
                          }

                          for (Object key : todayAcyivityResponse.graph_point.keySet()) {
                              GarphpointDetails garphpointDetails=new GarphpointDetails();
                              Double value=(Double)todayAcyivityResponse.graph_point.get(key);
                              point=value.floatValue();
                              garphpointDetails.setId(key.toString());
                              garphpointDetails.setValue(point);
                              garphpointDetails1.add(garphpointDetails);

                          }

                           if (point==0)
                           {
                               pieChart.setVisibility(View.GONE);
                           }
                           else {
                               pieEntries = new ArrayList<>();
                               pieChart.setVisibility(View.VISIBLE);
                             for (int i=0;i<garphpointDetails1.size();i++)
                             {
                                 String id =garphpointDetails1.get(i).getId();
                                 point =garphpointDetails1.get(i).getValue();

                                 if (id.equalsIgnoreCase("1"))
                                 {
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.language_icon)));
                                 }
                                 else if (id.equalsIgnoreCase("2")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.logic_icon)));

                                 }
                                 else if (id.equalsIgnoreCase("3")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.physical_icon)));

                                 }
                                 else if (id.equalsIgnoreCase("4")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.intrapersonal_icon)));

                                 }
                                 else if (id.equalsIgnoreCase("5")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.interpersonal_icon)));

                                 }
                                 else if (id.equalsIgnoreCase("6")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.spatial_icon)));

                                 }
                                 else if (id.equalsIgnoreCase("7")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.music_icon)));

                                 }
                                 else if (id.equalsIgnoreCase("8")){
                                     pieEntries.add(new PieEntry(point,"", getResources().getDrawable(R.drawable.environment_icon)));

                                 }

                                 final int[] MY_COLORS = {getResources().getColor(R.color.language), getResources().getColor(R.color.logic),
                                         getResources().getColor(R.color.physical), getResources().getColor(R.color.intrapersonal),
                                         getResources().getColor(R.color.interpersonal),getResources().getColor(R.color.spatial),
                                         getResources().getColor(R.color.music),getResources().getColor(R.color.environment)};
                                 ArrayList<Integer> colors = new ArrayList<Integer>();

                                 for(int c: MY_COLORS) colors.add(c);

                                 pieDataSet = new PieDataSet(pieEntries, "");
                                 pieData = new PieData(pieDataSet);
                                 pieData.setValueFormatter(new PercentFormatter(pieChart));
                                 pieChart.setData(pieData);

                                 pieChart.setHoleRadius(70.0f);
                                 pieChart.setTransparentCircleAlpha(0);

                                 pieDataSet.setColors(colors);
                                 pieDataSet.setValueTextColor(Color.BLACK);
                                 pieDataSet.setValueTextSize(10f);
                                 pieDataSet.setSelectionShift(0f);
                                 pieChart.getData().setDrawValues(false);

                                 pieChart.setRotationEnabled(false);
                                 pieDataSet.setIconsOffset(new MPPointF(0, 50));
                                 pieChart.getDescription().setEnabled(false);

                                 pieChart.setExtraOffsets(40, 0, 40, 0);

                                 Legend legend = pieChart.getLegend();
                                 legend.setEnabled(true);

                             }

                           }
                      }
                }
                else
                    {
                    Logout.L(context);
                }
            }

            @Override
            public void onFailure(Call<TodayAcyivityResponse> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }

    private void checkNetWorkAlarm() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                setAlram();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }


    private void checkNetWorkComplit() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                setCompletedActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }
    private void setCompletedActivity() {
        binding.loading.setVisibility(View.VISIBLE);
        activitySendCheck.activity_id=activity_id;
        activitySendCheck.login_token=Prefs.getLoginToken(context);
        activitySendCheck.child_id=child_id;
        Call<AcyivitySendComResponse> call = ApiClient.getRetrofit().create(Api.class).seAcyivitySendComResponseCall(activitySendCheck);
        call.enqueue(new Callback<AcyivitySendComResponse>() {
            @Override
            public void onResponse(Call<AcyivitySendComResponse> call, Response<AcyivitySendComResponse> response) {
                binding.loading.setVisibility(View.GONE);
                AcyivitySendComResponse response1= response.body();
                     if (response1 !=null)
                     {
                         if(response1.error.equals("false")){
                             H.T(context,response1.message);
                             binding.btnRegister.setText(getResources().getString(R.string.do_it_again));
                         }else {
                             H.T(context,response1.message);
                         }
                     }
            }

            @Override
            public void onFailure(Call<AcyivitySendComResponse> call, Throwable t) {
              //  Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAlram() {
        binding.loading.setVisibility(View.VISIBLE);
        alarmCheck.activity_id=activity_id;
        alarmCheck.login_token=Prefs.getLoginToken(context);
        alarmCheck.child_id=child_id;
        alarmCheck.is_alarm_set=isalarmset;
        Call<AlarmResponse> call = ApiClient.getRetrofit().create(Api.class).alarmResponse(alarmCheck);
        call.enqueue(new Callback<AlarmResponse>() {
            @Override
            public void onResponse(Call<AlarmResponse> call, Response<AlarmResponse> response) {
                binding.loading.setVisibility(View.GONE);
                AlarmResponse response1= response.body();
                H.L("responsealram=" + new Gson().toJson(response.body()));
                if(response1.error.equals("false")){

                    if (isalarmset)
                    {
                        binding.homeAlarm.setImageDrawable(getResources().getDrawable(R.drawable.alarm_off));
                        isalarmset=false;
                    }
                    else {
                        binding.homeAlarm.setImageDrawable(getResources().getDrawable(R.drawable.alarm));
                        isalarmset=true;
                    }
                    H.T(context,response1.message);
                }else {
                    H.T(context,response1.message);
                }
            }

            @Override
            public void onFailure(Call<AlarmResponse> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FROM_CAMERA) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
          //  addImage(bitmap);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
            img_base64=BitMapToString(resizedBitmap);
            checkNetWorkprofile();

        }

        else if (requestCode == FROM_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
                Bitmap   bitmap = BitmapFactory.decodeStream(imageStream);
             //   addImage(bitmap);
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                img_base64=BitMapToString(resizedBitmap);
                checkNetWorkprofile();
                H.L("img_base64"+img_base64);


            } catch (Exception e) {
                //e.printStackTrace();();
            }
        }


    }

    private void showCaptureDialog() {
        try {
            final CharSequence[] items = {getResources().getString(R.string.gallery), getResources().getString(R.string.camera)};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getResources().getString(R.string.take_photo));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    switch (item) {
                        case 0:
                            if (CheckPermission.checkDeviceOS()) {
                                if (checkPermission.checkStoragePermission()) {
                                    openGallery();
                                }
                            } else {
                                openGallery();
                            }
                            break;

                        case 1:
                            if (CheckPermission.checkDeviceOS()) {
                                if (checkPermission.checkCameraPermission()) {
                                    openCamera();
                                }

                            } else {
                                openCamera();
                            }
                            break;
                    }
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception e) {
            //H.L(e.toString());
        }
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), FROM_GALLERY);
    }
    private void openCamera() {
        try {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera_intent, FROM_CAMERA);

        } catch (Exception e) {
            //e.printStackTrace();();
        }

    }

    private String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
//String encodedImage = Base64.encode(b, Base64.DEFAULT);
        String encodedImage= Base64.encodeToString(b,Base64.DEFAULT);

        return encodedImage;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        try {
            //H.L("onRequestPermissionsResult");
            switch (requestCode) {
                case CheckPermission.STORAGE_PERMISSION_REQUEST_CODE:
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openGallery();

                    } else {
                        // Helper.LOG("Permission rejected");
                    }

                    break;
                case CheckPermission.CAMERA_PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        openCamera();
                    } else {
                        //  Helper.LOG("Permission rejected");
                    }
                    break;
            }
        } catch (Exception e) {
            //  Helper.LOG(e.toString());
        }
    }

    private void addImage(Bitmap bitmap) {
        try {
          binding .homeImg.setImageBitmap(bitmap);
        } catch (Exception e) {
            //e.printStackTrace();();
        }
    }

    private void checkNetWorkprofile() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                imageprofile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }

    private void imageprofile() {
        binding.loading.setVisibility(View.VISIBLE);
        childeImageCheck.login_token=Prefs.getLoginToken(context);
        childeImageCheck.child_id=child_id;
        childeImageCheck.img_base64=img_base64;
        childeImageCheck.activity_id=activity_id;
        Call<ProfileImageResponse> call = ApiClient.getRetrofit().create(Api.class).ImageResponse(childeImageCheck);
        call.enqueue(new Callback<ProfileImageResponse>() {
            @Override
            public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                H.L("responseprofile=" + new Gson().toJson(response.body()));
                binding.loading.setVisibility(View.GONE);
                ProfileImageResponse response1= response.body();
                if (response1 !=null && response.code() == 200) {
                    if (response1.error.equals("false")) {
                        H.T(context, response1.message);
                        img_base64="";
                        H.L("image-"+ response1.uploadedimg);
                        images.add(response1.uploadedimg);
                        adapterImageListing = new AdapterImageListing(context,images,binding.homeCamera);
                        binding.rvList.setAdapter(adapterImageListing);
                        adapterImageListing.notifyDataSetChanged();
                    } else {
                        H.T(context, response1.message);
                    }
                }else
                {
                    Logout.L(context);
                }
            }

            @Override
            public void onFailure(Call<ProfileImageResponse> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);

            }
        });
    }

    private void showpopupTitleDeedUpload() {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_video_upload);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER; dialog.getWindow().setAttributes(lp);

        EditText etyoutube = dialog.findViewById(R.id.etyoutube);
        Button btnNo = dialog.findViewById(R.id.btnNo);
        Button btnconfrim = dialog.findViewById(R.id.btnconfrim);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnconfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String video_url= etyoutube.getText().toString().trim();

                if (video_url.isEmpty())
                {
                    H.T(context,"Masukkan alamat url Youtube");
                }
                else if (!isYoutubeUrl(video_url))
                {
                    H.T(context,"Youtube Video url is not correct");
                }
                else {
                    checkNetWorkyoutube(video_url);
                    dialog.dismiss();
                }

            }
        });


        dialog.show();
    }

    private void checkNetWorkyoutube(String video_url) {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                youtubeurlsend(video_url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }

    private void youtubeurlsend(String video_url) {
        binding.loading.setVisibility(View.VISIBLE);
        videoCheck.login_token=Prefs.getLoginToken(context);
        videoCheck.child_id=child_id;
        videoCheck.video_url=video_url;
        videoCheck.activity_id=activity_id;
        Call<VideoResponse> call = ApiClient.getRetrofit().create(Api.class).videoResponse(videoCheck);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                H.L("responseprofile=" + new Gson().toJson(response.body()));
                binding.loading.setVisibility(View.GONE);
                VideoResponse response1= response.body();
                if (response1 !=null && response.code() == 200) {
                    if (response1.error.equals("false")) {
                        H.T(context, response1.message);

                    } else {
                        H.T(context, response1.message);
                    }
                }else
                {
                    Logout.L(context);
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                binding.loading.setVisibility(View.GONE);

            }
        });
    }

    public static boolean isYoutubeUrl(String youTubeURl)
    {
        boolean success;
        String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
        if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern))
        {
            success = true;
        }
        else
        {
            // Not Valid youtube URL
            success = false;
        }
        return success;
    }
    private class DownloadTask extends AsyncTask<URL,Integer,List<Bitmap>>{
        protected void onPreExecute(){
            // Display the progress dialog on async task start
            mProgressDialog.show();

        }
        @Override
        protected List<Bitmap> doInBackground(URL... urls) {

            int count = urls.length;
            //URL url = urls[0];
            HttpURLConnection connection = null;
            List<Bitmap> bitmaps = new ArrayList<>();

            // Loop through the urls
            for(int i=0;i<count;i++){
                URL currentURL = urls[i];
                // So download the image from this url
                try{
                    // Initialize a new http url connection
                    connection = (HttpURLConnection) currentURL.openConnection();

                    // Connect the http url connection
                    connection.connect();

                    // Get the input stream from http url connection
                    InputStream inputStream = connection.getInputStream();

                    // Initialize a new BufferedInputStream from InputStream
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                    // Convert BufferedInputStream to Bitmap object
                    Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                    // Add the bitmap to list
                    bitmaps.add(bmp);
                    // add the url to list URL
                   // imageName.add(currentURL);

                    // Publish the async task progress
                    // Added 1, because index start from 0
                    publishProgress((int) (((i+1) / (float) count) * 100));
                    if(isCancelled()){
                        break;
                    }

                }catch(IOException e){
                    e.printStackTrace();
                }finally{
                    // Disconnect the http url connection
                    assert connection != null;
                    connection.disconnect();
                }
            }
            return bitmaps;
        }

        // When all async task done
        @SuppressLint("WrongThread")
        protected void onPostExecute(List<Bitmap> result){
            // Hide the progress dialog
            mProgressDialog.dismiss();
            ArrayList<Uri> imageUris = new ArrayList<Uri>();

            for(int i=0;i<result.size();i++){
                Bitmap bitmap = result.get(i);
                // Save the bitmap to internal storage
             //   Uri imageInternalUri = (bitmap, i);
                // Display the bitmap from memory
            //    addNewImageViewToLayout(bitmap);

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
              //  String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title2", null);

                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        bitmap, "Title2", null);
                H.L("path"+path);
                Uri urluri=getImageUri(context,bitmap);
                imageUris.add(urluri);

            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_TEXT, "Hello");
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share with"));
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title2", null);
        return Uri.parse(path);
    }
    protected URL stringToURL(String url1) {
        try {
            url = new URL(url1);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            // stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }
    private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // binding = null;
    }


}