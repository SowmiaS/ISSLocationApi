package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wethejumpingspiders.isslocationapi.sightingsparser.Feed
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingRSSService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LocationPointParser {

    private val delimeter = "],["
    private val prefix = "var addressPoints = [["
    private val suffix = "]];"
    private val locationSurroundingDelimeter = "'"



    suspend fun parseLocationPoints(input: String): List<LocationPoint>  {
        return GlobalScope.async { input.trim().removePrefix(prefix).removeSuffix(suffix).split(delimeter)
            .map { it -> getLocationPoint(it.trim()) }}.await()
    }

    private fun getLocationPoint(text: String): LocationPoint {
        System.out.println(text)
        val regex = getRegexString().toRegex()
        return regex.matchEntire(text)
            ?.destructured
            ?.let { (name, latitude, longitude, requestId1, requestId2, requestId3) ->
                LocationPoint(
                    0,
                    name.removeSurrounding(locationSurroundingDelimeter).trim(),
                    latitude.toFloat(),
                    longitude.toFloat(),
                    requestId1.removeSurrounding(locationSurroundingDelimeter).trim(),
                    requestId2.removeSurrounding(locationSurroundingDelimeter).trim(),
                    requestId3.removeSurrounding(locationSurroundingDelimeter).trim()
                )
            }
            ?:
            throw IllegalArgumentException(text)
    }


    private fun getRegexString(): String {
        val addressRegex = "(^'[-.0-9A-Za-z _\\\\s]+, [-.A-Za-z _\\\\s]+')"
        val locationNameRegex = "('[-.0-9A-Za-z _\\\\s]+')"
        val decimalRegex = "(-*\\d*\\.\\d+)"
        return "$addressRegex,$decimalRegex,$decimalRegex,$locationNameRegex,$locationNameRegex,$locationNameRegex";
    }


}

@Entity (tableName = "locationpoint")
data class LocationPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val latitude: Float,
    val longitude: Float,
    val region: String,
    @ColumnInfo(name = "country")
    val country: String,
    val city: String
)


