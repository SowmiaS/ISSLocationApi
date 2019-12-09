package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.content.Context
import android.util.Log
import com.wethejumpingspiders.isslocationapi.locationpointparser.room.LocationPointsDatabaseHelper
import kotlinx.coroutines.*
import kotlin.collections.ArrayList


interface LocationPointManagerInterface {

    suspend fun syncLocationPoints()
    suspend fun storeLocationPointsInRoom(locationPoints: List<LocationPoint>)
    suspend fun getAllLocationPoints(): List<LocationPoint>?
    suspend fun getMatchedLocationPoint(latitude: Float, longitude: Float): LocationPoint?
    suspend fun getLocationPoint(id : Int) : LocationPoint?

}

interface OnLocationPointDownload {

    fun onLocationPointChanged(locationPoints: List<LocationPoint>)
    fun onFailure(t: Throwable)
}

class LocationPointManager(val context: Context) : LocationPointManagerInterface {

    val databaseHelper = LocationPointsDatabaseHelper(context)
    val downloader = LocationPointDownloader()
    val matcher = LocationPointMatcher(context, databaseHelper)
    val parser = LocationPointParser()


    override suspend fun syncLocationPoints(){
        val locationPointsJSString = downloader.getLocationPointsJS()
        val locationPointsList = parser.parseLocationPoints(locationPointsJSString)
        locationPointsList?.let {
            databaseHelper.deleteAllLocationPoints()
            databaseHelper.addAllLocationPoints(locationPointsList)
        }
    }

    override suspend fun storeLocationPointsInRoom(locationPoints: List<LocationPoint>) {
        databaseHelper.addAllLocationPoints(locationPoints)
    }

    override suspend fun getAllLocationPoints(): List<LocationPoint>? {
        return databaseHelper.getAllLocationPoints()
    }

    override suspend fun getMatchedLocationPoint(latitude: Float, longitude: Float): LocationPoint? {
        return matcher.getClosestLocationPoint(latitude, longitude)
    }

    override suspend fun getLocationPoint(id: Int) : LocationPoint? {
        return databaseHelper.getLocationPoint(id)
    }
}
