package com.example.survey.Model;

import com.google.gson.annotations.SerializedName;

public class PostPutDelSurvey {
    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private Survey mSurvey;
    @SerializedName("message")
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Survey getmSurvey() {
        return mSurvey;
    }

    public void setmSurvey(Survey mSurvey) {
        this.mSurvey = mSurvey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
