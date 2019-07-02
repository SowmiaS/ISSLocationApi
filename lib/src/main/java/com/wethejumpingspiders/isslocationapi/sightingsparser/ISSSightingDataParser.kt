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

            val date = desc.component1().trim().removePrefix("Date:")
            val time = desc.component2().trim().removePrefix("Time:")
            val duration = desc.component3().trim().removePrefix("Duration:")
            val maxElevation = desc.component4().trim().removePrefix("Maximum Elevation:")
            val approach = desc.component5().trim().removePrefix("Approach:")
            val sightingInfo = SightingInfo(0,date, time, duration, maxElevation, approach, "",locationPoint.id)
            sightingInfoList.add(sightingInfo)
        }
        return sightingInfoList

    }

}