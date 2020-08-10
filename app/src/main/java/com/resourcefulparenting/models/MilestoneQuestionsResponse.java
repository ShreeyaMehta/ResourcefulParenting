package com.resourcefulparenting.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MilestoneQuestionsResponse {
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("error")
    @Expose
    public String error;
    @SerializedName("questionDetails")
    @Expose
    public ArrayList<MilestoneQuestionsResponse.QuestionDetails> result_data = null;


    public class QuestionDetails  implements Serializable {


        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("question")
        @Expose
        public String question;
        @SerializedName("answer")
        @Expose
        public boolean checked;


    }

}
