package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wethejumpingspiders.isslocationapi.sightingsparser.Feed
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingRSSService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LocationPointParser {

    private val delimeter = "],["
    private val prefix = "var addressPoints = [["
    private val suffix = "];"


    fun parseLocationPoints(input: String): List<LocationPoint> {
        return input.trim().removePrefix(prefix).removeSuffix(suffix).split(delimeter)
            .map { it -> getLocationPoint(it.trim()) }
    }

    private fun getLocationPoint(text: String): LocationPoint {

        val regex = getRegexString().toRegex()
        return regex.matchEntire(text)
            ?.destructured
            ?.let { (name, latitude, longitude, requestId1, requestId2, requestId3) ->
                LocationPoint(
                    0L,
                    name,
                    latitude.toFloat(),
                    longitude.toFloat(),
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

@Entity
data class LocationPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val region: String,
    val country: String,
    val city: String
)


