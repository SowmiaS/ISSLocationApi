package com.wethejumpingspiders.isslocationapi.sightingsparser

import android.util.Log
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class SightingInfoDownloader {

    val RSSFEEDURL = "https://spotthestation.nasa.gov//sightings/"


    public suspend fun getSightingsDesc(locationPoint: LocationPoint): ArrayList<String>? {

        val retrofit = Retrofit.Builder().baseUrl(RSSFEEDURL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
        val rssapi = retrofit.create(SightingRSSService::class.java)
        val feedResponse = rssapi.getFeed(locationPoint.country, locationPoint.region, locationPoint.city)

        if (feedResponse.body() == null) return null;
        else {
            val sightingList = ArrayList<String>();
            feedResponse.body()?.mChannel?.sightingItems?.forEach { item ->
                Log.d("RSSFEED", item.description)
                item.description?.let { sightingList.add(it) }

            }
            return sightingList
        }
    }


}