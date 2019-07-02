package com.wethejumpingspiders.isslocationapi.sightingsparser

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SightingRSSService {

    @GET("indexrss.cfm")
    suspend fun getFeed(@Query("country")  country : String,
                @Query("region")  region : String,
                @Query("city")  city : String
                ) : Response<Feed>
}
