package com.wethejumpingspiders.isslocationapi.sightingsparser

import android.util.Log
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory.create


class ISSSightingDataParser() {

    val RSSFEEDURL = "https://spotthestation.nasa.gov/sightings/"

    public fun parseSightingInfos(locationPoint: LocationPoint, sightingDescriptionList: List<String>): List<SightingInfo> {

        val sightingInfoList: ArrayList<SightingInfo> = ArrayList()
        for (sightingDescription in sightingDescriptionList) {
            val desc = sightingDescription.split("<br/>")

            val date = desc.get(0).trim().removePrefix("Date:")
            val time = desc.get(1).trim().removePrefix("Time:")
            val duration = desc.get(2).trim().removePrefix("Duration:")
            val maxElevation = desc.get(3).trim().removePrefix("Maximum Elevation:")
            val approach = desc.get(4).trim().removePrefix("Approach:")
            val departure = desc.get(5).trim().removePrefix("Departure:")
            val sightingInfo = SightingInfo(0,date, time, duration, maxElevation, approach, departure,locationPoint.id)
            sightingInfoList.add(sightingInfo)
        }
        return sightingInfoList

    }

}