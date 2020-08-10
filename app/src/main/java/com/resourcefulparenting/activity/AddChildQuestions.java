package com.resourcefulparenting.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.resourcefulparenting.R;
import com.resourcefulparenting.models.AddChild.AddChildCheck;
import com.resourcefulparenting.models.AddChild.AddChildResponse;
import com.resourcefulparenting.models.AddChild.Queries;
import com.resourcefulparenting.models.QueriesResponse;
import com.resourcefulparenting.network.Api;
import com.resourcefulparenting.network.ApiClient;
import com.resourcefulparenting.util.CheckNetworkConnection;
import com.resourcefulparenting.util.H;
import com.resourcefulparenting.util.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChildQuestions extends AppCompatActivity  {

    private Context context;
    private String login_token, name, gender, date_, month_, year_;
    String points = "0";
   // private boolean click = true;
    TextView question,tv_total;
    Button btn1, btn2, btn3, btn4, btn5, next, back, submit;
    private RelativeLayout loading;
    private int current_question = 0;
    ImageView img_back;

    ArrayList<QueriesResponse.Query> result;
    Queries queries[] = {};
    final AddChildCheck addChildCheck = new AddChildCheck();
    JSONObject jObjd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_questions);
        context = this;
      //  Prefs.setCurrentActivity(context, Prefs.CurrentActivity.ADDCHILDNAME);
        loading = findViewById(R.id.loading);

  /*      SharedPreferences settings=getSharedPreferences("prefs",0);
        login_token = settings.getString("login_token", login_token);*/

        name = getIntent().getStringExtra("name");
        gender = getIntent().getStringExtra("gender");
        date_ = getIntent().getStringExtra("date");
        month_ = getIntent().getStringExtra("month");
        year_ = getIntent().getStringExtra("year");
 /*       login_token = getIntent().getStringExtra("login_token");

        Toast.makeText(context, login_token , Toast.LENGTH_SHORT).show();*/
        bindView();

       // Log.d("LinkedHaspMap", Main.main();)

        checkNetWork();

    }

    private void bindView() {
        img_back =findViewById(R.id.img_back);
        question = findViewById(R.id.inside_tv_question);
        tv_total = findViewById(R.id.tv_total);
        next = findViewById(R.id.inside_next);
        back = findViewById(R.id.inside_back);
      //  submit = findViewById(R.id.inside_submit);
        btn1 = findViewById(R.id.inside_rate_1);
        btn2 = findViewById(R.id.inside_rate_2);
        btn3 = findViewById(R.id.inside_rate_3);
        btn4 = findViewById(R.id.inside_rate_4);
        btn5 = findViewById(R.id.inside_rate_5);



        btn1.setOnClickListener(v -> {
            points = "1";
            btn1.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
            result.get(current_question).is_check = true;
            result.get(current_question).points=points;

        });

        btn2.setOnClickListener(v -> {
            points = "2";
            btn2.setBackgroundResource(R.drawable.orange_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
            result.get(current_question).is_check = true;
            result.get(current_question).points=points;
        });

        btn3.setOnClickListener(v -> {
            points = "3";
            btn3.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
            result.get(current_question).points=points;
            result.get(current_question).is_check = true;
        });

        btn4.setOnClickListener(v -> {
            points = "4";
            btn4.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            btn5.setBackgroundResource(R.drawable.blue_bg);
            result.get(current_question).points=points;
            result.get(current_question).is_check = true;
        });

        btn5.setOnClickListener(v -> {
            points = "5";
            btn5.setBackgroundResource(R.drawable.orange_bg);
            btn2.setBackgroundResource(R.drawable.blue_bg);
            btn3.setBackgroundResource(R.drawable.blue_bg);
            btn4.setBackgroundResource(R.drawable.blue_bg);
            btn1.setBackgroundResource(R.drawable.blue_bg);
            result.get(current_question).is_check = true;
            result.get(current_question).points=points;
        });

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current_question < (result.size() - 1)) {
                    if (result.get(current_question).is_check)
                    {

                        current_question++;
                        setQuestion(current_question);
                        btn4.setBackgroundResource(R.drawable.blue_bg);
                        btn2.setBackgroundResource(R.drawable.blue_bg);
                        btn3.setBackgroundResource(R.drawable.blue_bg);
                        btn1.setBackgroundResource(R.drawable.blue_bg);
                        btn5.setBackgroundResource(R.drawable.blue_bg);
                    }
                    else
                    {
                        H.T(context,"Select answer");
                    }


                } else {

                    next.setText("Submit");
                //    if (click) {
                    checkNetWorkAddChild();
                }


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                   if (current_question==0)
                   {

                   }
                   else
                   {
                       next.setText("Next");
                       current_question--;
                       setQuestion(current_question);
                   }


            }
        });


    }

    private void checkNetWork() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                getQuestions();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }


    private void getQuestions() {
        loading.setVisibility(View.VISIBLE);
        Call<QueriesResponse> call = ApiClient.getRetrofit().create(Api.class).getQueries();
        call.enqueue(new Callback<QueriesResponse>() {
            @Override
            public void onResponse(Call <QueriesResponse> call, Response<QueriesResponse> response) {
                loading.setVisibility(View.GONE);
                H.L("response=" + new Gson().toJson(response.body()));
             /*   adapter = new TestAdapter(context, response.body(), AddChildQuestions.this);
                viewPager.setAdapter(adapter);*/
            //    viewPager.beginFakeDrag();
                QueriesResponse page_model = response.body();
                result = page_model.result_data;
                setQuestion(current_question);
            }

            @Override
            public void onFailure(Call<QueriesResponse> call, Throwable t) {
              //  Toast.makeText(AddChildQuestions.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setQuestion(int position) {
        if (position < result.size() && result.size() != 0) {
            tv_total.setText(current_question+1+"/"+result.size());
            setTextQuesion(Html.fromHtml(result.get(position).question) + "");
        }
    }
    public void setTextQuesion(String question1) {
        question.setText(question1);

/*
        if (result.get(current_question).points!=null)
        {
            if (result.get(current_question).points.equals("1"))
            {
                btn1.setBackgroundResource(R.drawable.orange_bg);

            }
             else if(result.get(current_question).points.equals("2"))
            {
                btn2.setBackgroundResource(R.drawable.orange_bg);

            }  else if(result.get(current_question).points.equals("3"))
            {
                btn3.setBackgroundResource(R.drawable.orange_bg);

            }   else if(result.get(current_question).points.equals("4"))
            {
                btn4.setBackgroundResource(R.drawable.orange_bg);

            }   else if(result.get(current_question).points.equals("5"))
            {
                btn5.setBackgroundResource(R.drawable.orange_bg);

            }

        }
*/

    }

    private void checkNetWorkAddChild() {
        if (CheckNetworkConnection.getInstance(context).haveNetworkConnection()) {
            try {
                AddChild();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ///hud.dismiss();
            H.T(context, getString(R.string.no_internet_connection));
        }
    }


  /*  private void AddChild(JSONArray jArry) {
        loading.setVisibility(View.VISIBLE);
        addChildCheck.child_name=name;
        addChildCheck.child_gender=gender;
        addChildCheck.child_birth_date=date_;
        addChildCheck.child_birth_month=month_;
        addChildCheck.child_birth_year=year_;
        addChildCheck.queries=jArry.toString();
        Call<AddChildResponse> call = ApiClient.getRetrofit().create(Api.class).AddChild(addChildCheck);
        call.enqueue(new Callback<AddChildResponse>() {
            @Override
            public void onResponse(Call<AddChildResponse> call, Response<AddChildResponse> response) {
              //  if(login_token.equals(addChildCheck.login_token)){
                H.L("responseadd=" + new Gson().toJson(response.body()));
                    loading.setVisibility(View.GONE);
                    Intent next = new Intent(context, MainActivity.class);
                    startActivity(next);
                    finish();
              //  }
            }

            @Override
            public void onFailure(Call<AddChildResponse> call, Throwable t) {

            }
        });
    }*/

    private void AddChild() {
        JSONArray jArry = new JSONArray();
        for (int i = 0; i < result.size(); i++) {
            try {
                jObjd = new JSONObject();
                jObjd.put(result.get(i).id, result.get(i).points);
                jArry.put(jObjd);
                //  click=false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        loading.setVisibility(View.VISIBLE);
        addChildCheck.login_token = Prefs.getLoginToken(context);
        addChildCheck.child_name=name;
        addChildCheck.child_gender=gender;
        addChildCheck.child_birth_date=date_;
        addChildCheck.child_birth_month=month_;
        addChildCheck.child_birth_year=year_;
        addChildCheck.queries=jArry;
        H.L("Array:"+jArry);
        Call<AddChildResponse> call = ApiClient.getRetrofit().create(Api.class).AddChild(addChildCheck);
        call.enqueue(new Callback<AddChildResponse>() {
            @Override
            public void onResponse(Call<AddChildResponse> call, Response<AddChildResponse> response) {
                H.L("responsenana=" + new Gson().toJson(response.body()));
                loading.setVisibility(View.GONE);
                AddChildResponse addChildResponse = response.body();
                if (addChildResponse != null) {
                    if (addChildResponse.error.equals("false")) {
                        H.T(context,addChildResponse.message);
                        try {
                            JSONArray jsonArray1 ;
                            String oldjsonArray = Prefs.getChildDetails(context);
                            if (oldjsonArray.equalsIgnoreCase(""))
                            {
                                jsonArray1 = new JSONArray();
                                //   H.T(context,"call1");
                            }else {
                                //   H.T(context,"call2");
                                jsonArray1 = new JSONArray(oldjsonArray);
                            }
                            JSONObject object = new JSONObject();
                            object.put("child_id", addChildResponse.childDetails.id);
                            object.put("child_name", name);
                            jsonArray1.put(object);
                          //  Log.d("data",jsonArray1.toString());
                            Prefs.setChildDetails(context, jsonArray1.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Intent next = new Intent(context, MainActivity.class);
                        startActivity(next);
                        finish();

                    }
                    else
                    {
                        H.T(context,addChildResponse.message);
                    }
                }

            }

            @Override
            public void onFailure(Call<AddChildResponse> call, Throwable t) {
                loading.setVisibility(View.GONE);
            }
        });
    }

/*


    @Override
    public void submit(JSONArray items) {
        Log.d("Main item", String.valueOf(items));

        addChildCheck.login_token = login_token;
        addChildCheck.child_name = name;
        addChildCheck.child_gender = gender;
        addChildCheck.child_birth_date = date_;
        addChildCheck.child_birth_month = month_;
        addChildCheck.child_birth_year = year_;
        addChildCheck.queries = queries;

        loading.setVisibility(View.VISIBLE);
        //getAddChild();
    }
*/
}