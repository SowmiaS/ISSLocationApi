package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.wethejumpingspiders.isslocationapi.locationpointparser.room.LocationPointsDatabaseHelper

class LocationPointMatcher(val context: Context, val databaseHelper: LocationPointsDatabaseHelper) {

    fun getClosestLocationPoint(latitude: Float, longitude: Float): LocationPoint? {
        val countryName = getCountryName(latitude, longitude)
        val locationPointsList: List<LocationPoint>? = getLocationPointsForCountry(countryName)
        val distanceArray = mapDistance(latitude, longitude, locationPointsList)
        return findClosestLocationPoint(distanceArray, locationPointsList)
    }

    private fun getCountryName(latitude: Float, longitude: Float): String {
        val geocoder: Geocoder = Geocoder(context)
        val addresses: List<Address> = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
        return addresses.get(0).countryName
    }

    private fun getLocationPointsForCountry(countryName: String): List<LocationPoint>? {
        return databaseHelper.getAllLocationPoints(countryName)
    }

    private fun mapDistance(latitude: Float, longitude: Float, locationPoints: List<LocationPoint>?): Array<Double>? {
        locationPoints?.let {
            val locationPointsArr: Array<Double> = arrayOf<Double>()
            for ((index, locationPoint) in locationPoints.withIndex()) {
                locationPointsArr[index] = distance(
                    latitude.toDouble(),
                    longitude.toDouble(),
                    locationPoint.latitude.toDouble(),
                    locationPoint.longitude.toDouble()
                )
            }
            return locationPointsArr
        }
        return null
    }

    private fun findClosestLocationPoint(
        distanceArray: Array<Double>?,
        locationPoints: List<LocationPoint>?
    ): LocationPoint? {
        val minValueIndex = distanceArray?.indexOf(distanceArray.min())
        return locationPoints?.get(minValueIndex ?: 0)
    }


    private fun distance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {

        val earthRadius = 3958.75 // in miles, change to 6371 for kilometer output
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = Math.sin(dLat / 2)
        val sindLng = Math.sin(dLng / 2)

        val a = Math.pow(sindLat, 2.0) + (Math.pow(sindLng, 2.0)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return earthRadius * c // output distance, in MILES
    }

}