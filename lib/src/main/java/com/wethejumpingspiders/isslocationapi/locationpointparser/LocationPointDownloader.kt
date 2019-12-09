package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class LocationPointDownloader {

    val LOCATION_POINT_URL = "https://spotthestation.nasa.gov/js/"

    suspend fun getLocationPointsJS(): String? {
            val retrofit = Retrofit.Builder().baseUrl(LOCATION_POINT_URL)
                .build()
        try{
            val rssapi = retrofit.create(LocationPointJSService::class.java)
            val response = rssapi.getLocationPoints()
            if (response.body() == null || !response.isSuccessful) return null;
            else {
                return response.body()?.string()
            }
        }catch (e : Exception){
            return null
        }
    }
}

