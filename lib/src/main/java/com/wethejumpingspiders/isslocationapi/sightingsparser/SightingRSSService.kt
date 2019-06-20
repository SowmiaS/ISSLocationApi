package com.wethejumpingspiders.isslocationapi.sightingsparser

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SightingRSSService {

    @GET("indexrss.cfm")
    fun getFeed(@Query("country")  country : String,
                @Query("region")  region : String,
                @Query("city")  city : String
                ) : Call<Feed>
}
