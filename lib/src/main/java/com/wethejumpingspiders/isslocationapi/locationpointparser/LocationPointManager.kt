package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.content.Context
import com.wethejumpingspiders.isslocationapi.locationpointparser.room.LocationPointsDatabaseHelper
import kotlinx.coroutines.*
import kotlin.collections.ArrayList


interface LocationPointManagerInterface {

    fun downloadLocationPoints(listener: OnLocationPointDownload)
    fun storeLocationPointsInRoom(locationPoints: List<LocationPoint>)
    fun getAllLocationPoints(): List<LocationPoint>?
    fun getMatchedLocationPoint(latitude: Float, longitude: Float): LocationPoint?

}

interface OnLocationPointDownload {

    fun onLocationPointChanged(locationPoints: List<LocationPoint>)
    fun onFailure(t: Throwable)
}

class LocationPointManager(val context: Context) : LocationPointManagerInterface, LocationPointDownloaderInterface {

    val databaseHelper = LocationPointsDatabaseHelper(context)
    val downloader = LocationPointDownloader()
    val matcher = LocationPointMatcher(context, databaseHelper)
    val parser = LocationPointParser()

    var listener: OnLocationPointDownload? = null;

    override fun downloadLocationPoints(listener: OnLocationPointDownload) {
        this.listener = listener
        databaseHelper.deleteAllLocationPoints()
        downloader.getLocationPointsJS(this)
    }

    override fun storeLocationPointsInRoom(locationPoints: List<LocationPoint>) {
        databaseHelper.addAllLocationPoints(locationPoints)
    }

    override fun getAllLocationPoints(): List<LocationPoint>? {
        return databaseHelper.getAllLocationPoints()
    }

    override fun getMatchedLocationPoint(latitude: Float, longitude: Float): LocationPoint? {
        return matcher.getClosestLocationPoint(latitude, longitude)
    }


    override fun onSuccess(feed: String) {
        listener?.onLocationPointChanged(parser.parseLocationPoints(feed))
    }

    override fun onFailure(t: Throwable) {
        listener?.onFailure(t)
    }
}
