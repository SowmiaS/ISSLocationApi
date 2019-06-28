package com.wethejumpingspiders.isslocationapi.locationpointparser

import com.wethejumpingspiders.isslocationapi.sightingsparser.Feed
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LocationPointJSService{
    @GET("marker_list.js")
    suspend fun getLocationPoints() : Response<ResponseBody>
}