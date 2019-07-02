package com.wethejumpingspiders.isslocationapi.sightingsparser

import android.util.Log
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class SightingInfoDownloader{

    val RSSFEEDURL = "https://spotthestation.nasa.gov//sightings/"



    public suspend fun getSightingsDesc(locationPoint: LocationPoint): ArrayList<String> {

        val retrofit = Retrofit.Builder().baseUrl(RSSFEEDURL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        val rssapi = retrofit.create(SightingRSSService::class.java)
        val feedResponse = rssapi.getFeed(locationPoint.country, locationPoint.region, locationPoint.city)
        val sightingList = ArrayList<String>();
        feedResponse.body()?.mChannel?.sightingItems?.forEach { item ->
            Log.d("RSSFEED", item.description)
            item.description?.let { sightingList.add(it) }
        }

//        call.enqueue(object : Callback<Feed> {
//            override fun onFailure(call: Call<Feed>, t: Throwable) {
//                Log.d("ISSSightingDataParser", "onFailure")
//                //TODO : Need to handle this error case
//            }
//
//            override fun onResponse(call: Call<Feed>, response: Response<Feed>) {
//                if (response.isSuccessful) {
//
//                    val feed = response.body()
//                    feed?.mChannel?.sightingItems?.forEach { item ->
//                        Log.d("RSSFEED", item.description)
//                        item.description?.let { sightingList.add(it) }
//                    }
//                } else {
//                    Log.d("ISSSightingDataParser", "on Response Unsuccessful")
//                    //TODO : Need to handle this error case
//                }
//            }Response{protocol=http/1.1, code=404, message=Not Found, url=https://spotthestation.nasa.gov//sightings/xml_files/None_India_Coimbatore.xml}
//        })
        return sightingList

    }

}