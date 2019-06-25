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

    fun getSightingInfos(locationPoint: LocationPoint): List<SightingInfo> {

        val sightingInfoDescList = getSightingsDesc(locationPoint)
        return parseSightingInfos(locationPoint, sightingInfoDescList)

    }

    private fun getSightingsDesc(locationPoint: LocationPoint): ArrayList<String> {


        val retrofit = Retrofit.Builder().baseUrl(RSSFEEDURL)
            .addConverterFactory(create())
            .build()
        val rssapi = retrofit.create(SightingRSSService::class.java)
        val call = rssapi.getFeed(locationPoint.region, locationPoint.country, locationPoint.city)
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


    private fun parseSightingInfos(locationPoint: LocationPoint, sightingDescriptionList: List<String>): List<SightingInfo> {

        val sightingInfoList: ArrayList<SightingInfo> = ArrayList()
        for (sightingDescription in sightingDescriptionList) {
            val desc = sightingDescription.split("<br/>")

            val date = desc.component1().removePrefix("Date:")
            val time = desc.component2().removePrefix("Time:")
            val duration = desc.component3().removePrefix("Duration:")
            val maxElevation = desc.component4().removePrefix("Maximum Elevation:")
            val approach = desc.component5().removePrefix("Approach:")
            val sightingInfo: SightingInfo = SightingInfo(0,date, time, duration, maxElevation, approach, "",locationPoint.id)
            sightingInfoList.add(sightingInfo)
        }
        return sightingInfoList

    }


}