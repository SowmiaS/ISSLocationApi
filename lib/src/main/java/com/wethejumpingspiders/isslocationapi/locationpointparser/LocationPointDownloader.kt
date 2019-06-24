package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

class LocationPointDownloader {

    val LOCATION_POINT_URL = "https://spotthestation.nasa.gov/js/"

    fun getLocationPointsJS(listener: LocationPointDownloaderInterface) {
        val retrofit = Retrofit.Builder().baseUrl(LOCATION_POINT_URL)
            .build()
        val rssapi = retrofit.create(LocationPointJSService::class.java!!)
        val call = rssapi.getLocationPoints()
        val sightingList = ArrayList<String>();

        call.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ISSSightingDataParser", "onFailure")
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val feed = response.body()?.string()
                    feed?.let {
                        listener.onSuccess(feed)
                    }
                }
            }
        })
        }
    }

    interface LocationPointDownloaderInterface {
        fun onSuccess(feed: String)
        fun onFailure(t:Throwable)
    }