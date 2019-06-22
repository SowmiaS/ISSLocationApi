package com.wethejumpingspiders.isslocationapi.api

import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.OnSightingInfoResponse
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo

private interface ISSLocatorInterface {

    /**
     * This method initialises the ISSLocator Api.
     */
    fun intialise()

    /**
     * This method retrieves the sighting information for the given location point and notifies to the listener.
     */
    fun getSightingInformation(locationPoint: LocationPoint, listener: OnSightingInfoResponse)

    /**
     * This method returns all location points for ISS Locator
     */
    fun getAllLocationPoints() : ArrayList<LocationPoint>

    /**
     * This method matches the given latitude and longitude with available ISS Location Points.
     */
    fun getMatchedLocationPoint(latitude: Float, longitue: Float) : LocationPoint

}




class ISSLocator : ISSLocatorInterface {

    override fun intialise() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getSightingInformation(locationPoint: LocationPoint, listener: OnSightingInfoResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllLocationPoints(): ArrayList<LocationPoint> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMatchedLocationPoint(latitude: Float, longitue: Float): LocationPoint {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}