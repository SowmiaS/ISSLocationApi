package com.wethejumpingspiders.isslocationapi.api

import android.content.Context
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPointManager
import com.wethejumpingspiders.isslocationapi.locationpointparser.OnLocationPointDownload
import com.wethejumpingspiders.isslocationapi.sightingsparser.OnSightingInfoResponse
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfoManager

private interface ISSLocatorInterface {

    /**
     * This method initialises the ISSLocator Api.
     */
    fun intialise(listener: ISSLocator.ISSLocatorInitializationStatus)


    /**
     * This method downloads the location points from server and refreshes the Database
     */
    //TODO Need to think of linking the sighting infos already saved.
    fun refreshLocationPoints()

    /**
     * This method retrieves the sighting information for the given location point and notifies to the listener.
     */
    fun getSightingInformation(locationPoint: LocationPoint, listener: OnSightingInfoResponse)

    /**
     * This method returns all location points for ISS Locator
     */
    fun getAllLocationPoints(): List<LocationPoint>?

    /**
     * This method matches the given latitude and longitude with available ISS Location Points.
     */
    fun getMatchedLocationPoint(latitude: Float, longitue: Float): LocationPoint?

}


class ISSLocator(val context: Context) : ISSLocatorInterface, OnLocationPointDownload {

    val sightingInfoManager = SightingInfoManager(context)
    val locationPointManager = LocationPointManager(context)
    var listener : ISSLocatorInitializationStatus? = null


    override fun intialise(listener: ISSLocatorInitializationStatus) {
        this.listener = listener;
        locationPointManager.getAllLocationPoints() ?.let {
            locationPointManager.downloadLocationPoints(this)
            return
        }
        listener.onSuccess()
    }

    override fun refreshLocationPoints() {
        locationPointManager.downloadLocationPoints(this)
    }

    override fun getSightingInformation(locationPoint: LocationPoint, listener: OnSightingInfoResponse) {
        sightingInfoManager.getSightingInfo(locationPoint, listener)
    }

    override fun getAllLocationPoints(): List<LocationPoint>? {
        return locationPointManager.getAllLocationPoints()
    }

    override fun getMatchedLocationPoint(latitude: Float, longitue: Float): LocationPoint? {
        return locationPointManager.getMatchedLocationPoint(latitude, longitue)
    }

    override fun onLocationPointChanged(locationPoints: List<LocationPoint>) {
        listener?.onSuccess()
        locationPointManager.storeLocationPointsInRoom(locationPoints)
        listener = null
    }

    override fun onFailure(t: Throwable) {
        listener?.onFailure()
        listener = null
    }

    interface ISSLocatorInitializationStatus{
        fun onSuccess()
        fun onFailure()
    }

}