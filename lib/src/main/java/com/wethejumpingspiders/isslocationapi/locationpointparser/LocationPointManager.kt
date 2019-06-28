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
        System.out.println("errr ...4")
        val locationPointsJSString = downloader.getLocationPointsJS()
        val locationPointsList = parser.parseLocationPoints(locationPointsJSString)
        databaseHelper.deleteAllLocationPoints()
        databaseHelper.addAllLocationPoints(locationPointsList)
        System.out.println("errr ...5")    }

    override suspend fun storeLocationPointsInRoom(locationPoints: List<LocationPoint>) {
        databaseHelper.addAllLocationPoints(locationPoints)
    }

    override suspend fun getAllLocationPoints(): List<LocationPoint>? {
        return databaseHelper.getAllLocationPoints()
    }

    override suspend fun getMatchedLocationPoint(latitude: Float, longitude: Float): LocationPoint? {
        return matcher.getClosestLocationPoint(latitude, longitude)
    }



}
