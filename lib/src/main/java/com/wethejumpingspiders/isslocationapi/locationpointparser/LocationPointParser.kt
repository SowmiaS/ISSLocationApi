package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.util.Log
import com.wethejumpingspiders.isslocationapi.sightingsparser.Feed
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingRSSService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class LocationPointParser {

    private val delimeter = "],["
    private val prefix = "var addressPoints = [["
    private val suffix = "];"
    val LOCATION_POINT_URL = "https://spotthestation.nasa.gov/js/"


    fun getLocationPointsJS() {
        val retrofit = Retrofit.Builder().baseUrl(LOCATION_POINT_URL)
            .build()
        val rssapi = retrofit.create(LocationPointJSService::class.java!!)
        val call = rssapi.getLocationPoints()
        val sightingList = ArrayList<String>();

        call.enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ISSSightingDataParser", "onFailure")
                //TODO : Need to handle this error case
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val feed = response.body()?.string()
                    feed?.let { parseLocationPoints(it) }
                }
            }
        })
    }

    private fun parseLocationPoints(input: String): List<LocationPoint> {
        return input.trim().removePrefix(prefix).removeSuffix(suffix).split(delimeter)
            .map { it -> getLocationPoint(it.trim()) }
    }

    private fun getLocationPoint(text: String): LocationPoint {

        val regex = getRegexString().toRegex()
        return regex.matchEntire(text)
            ?.destructured
            ?.let { (name, latitude, longitude, requestId1, requestId2, requestId3) ->
                LocationPoint(
                    name,
                    latitude,
                    longitude,
                    requestId1,
                    requestId2,
                    requestId3
                )
            }
            ?: throw IllegalArgumentException(text)
    }


    private fun getRegexString(): String {
        val addressRegex = "(^'[-.0-9A-Za-z _\\\\s]+, [-.A-Za-z _\\\\s]+')"
        val locationNameRegex = "('[-.0-9A-Za-z _\\\\s]+')"
        val decimalRegex = "(-*\\d*\\.\\d+)"
        return "$addressRegex,$decimalRegex,$decimalRegex,$locationNameRegex,$locationNameRegex,$locationNameRegex";
    }


}

data class LocationPoint(
    val name: String,
    val latitude: String,
    val longitude: String,
    val requestid1: String,
    val requestid2: String,
    val requestid3: String
)


