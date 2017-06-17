package com.trailit.trailit;

/**
 * Created by ananth on 19/3/17.
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ApiInterface {

    @POST("getData")
    Call<List<point>> getResult(@Body request rq);

}