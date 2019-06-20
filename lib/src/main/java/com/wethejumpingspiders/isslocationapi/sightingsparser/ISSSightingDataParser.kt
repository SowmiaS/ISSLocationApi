package com.wethejumpingspiders.isslocationapi.sightingsparser

import android.util.Log
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory.create


class ISSSightingDataParser() {

    val RSSFEEDURL = "https://spotthestation.nasa.gov//sightings/"

    fun getSightings(locationPoint: LocationPoint) : ArrayList<String> {

        val retrofit = Retrofit.Builder().baseUrl(RSSFEEDURL)
            .addConverterFactory(create())
            .build()
        val rssapi = retrofit.create(SightingRSSService::class.java!!)
        val call = rssapi.getFeed(locationPoint.requestid1, locationPoint.requestid2, locationPoint.requestid3)
        val sightingList = ArrayList<String>();

        call.enqueue(object : Callback<Feed> {
            override fun onFailure(call: Call<Feed>, t: Throwable) {
                Log.d("ISSSightingDataParser", "onFailure")
                //TODO : Need to handle this error case
            }

            override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
                if (response.isSuccessful) {

                    val feed = response.body()
                    feed?.mChannel?.sightingItems?.forEach { item ->
                        Log.d("RSSFEED", item.description)
                        item.description?.let { sightingList.add(it) }
                    }
                } else {
                    Log.d("ISSSightingDataParser", "on Response Unsuccessful")
                    //TODO : Need to handle this error case
                }
            }
        })
        return sightingList

    }


}