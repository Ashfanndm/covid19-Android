package com.ashfanndm.covid19srilankanews;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("get-current-statistical")
    Call<People> getPerson();
    //Call<List<People>> getPerson();
    //Call<Data> getPerson();

}
