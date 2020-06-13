package com.ashfanndm.covid19srilankanews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    private People people;

    @SerializedName("local_total_cases")
    @Expose
    private int reported;

    @SerializedName("local_active_cases")
    @Expose
    private int treatment;

    @SerializedName("local_recovered")
    @Expose
    private int healed;

    @SerializedName("local_total_number_of_individuals_in_hospitals")
    @Expose
    private int suspicious;

    @SerializedName("local_deaths")
    @Expose
    private int death;

    @SerializedName("update_date_time")
    @Expose
    private String time;

    @SerializedName("global_total_cases")
    @Expose
    private String globalTotal;

    @SerializedName("global_recovered")
    @Expose
    private String globalRecovered;

    @SerializedName("global_deaths")
    @Expose
    private String globalDeath;



    public String getTime() {
        return time;
    }

    public People getPeople() {
        return people;
    }

    public int getReported() {
        return reported;
    }

    public int getTreatment() {
        return treatment;
    }

    public int getHealed() {
        return healed;
    }

    public int getSuspicious() {
        return suspicious;
    }

    public int getDeath() {
        return death;
    }

    public String getGlobalTotal() {
        return globalTotal;
    }

    public String getGlobalRecovered() {
        return globalRecovered;
    }

    public String getGlobalDeath() {
        return globalDeath;
    }
}
