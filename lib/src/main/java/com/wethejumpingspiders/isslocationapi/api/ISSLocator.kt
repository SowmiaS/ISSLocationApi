package com.wethejumpingspiders.isslocationapi.api

import android.content.Context
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPointManager
import com.wethejumpingspiders.isslocationapi.sightingsparser.OnSightingInfoResponse
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfoManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import java.lang.Exception

private interface ISSLocatorInterface {

    /**
     * This method initialises the ISSLocator Api.
     */
    suspend fun intialise()


    /**
     * This method downloads the location points from server and refreshes the Database
     */
    //TODO Need to think of linking the sighting infos already saved.
    suspend fun refreshLocationPoints()

    /**
     * This method retrieves the sighting information for the given location point and notifies to the listener.
     */
    suspend fun getSightingInformation(locationPoint: LocationPoint) : List<SightingInfo>?

    /**
     * This method returns all location points for ISS Locator
     */
    suspend fun getAllLocationPoints(): List<LocationPoint>?

    /**
     * This method matches the given latitude and longitude with available ISS Location Points.
     */
    suspend fun getMatchedLocationPoint(latitude: Float, longitue: Float): LocationPoint?

}


class ISSLocator(val context: Context) : ISSLocatorInterface {

    val sightingInfoManager = SightingInfoManager(context)
    val locationPointManager = LocationPointManager(context)

    override suspend fun intialise() {
        System.out.println("errr ...1")
        val locationPoints = locationPointManager.getAllLocationPoints()
        if (locationPoints == null || locationPoints.size ==0) {
            System.out.println("errr ...2")
            locationPointManager.syncLocationPoints()
        }
    }

    override suspend fun refreshLocationPoints() {
        locationPointManager.syncLocationPoints()
    }

    override suspend fun getAllLocationPoints(): List<LocationPoint>? {
        return locationPointManager.getAllLocationPoints()
    }

    override suspend fun getMatchedLocationPoint(latitude: Float, longitue: Float): LocationPoint? {
        return locationPointManager.getMatchedLocationPoint(latitude, longitue)
    }

    override suspend fun getSightingInformation(locationPoint: LocationPoint) : List<SightingInfo>? {
        return sightingInfoManager.getSightingInfo(locationPoint)
    }
}

class ISSLocatorInitializationError(message: String) : Exception(message) {


}