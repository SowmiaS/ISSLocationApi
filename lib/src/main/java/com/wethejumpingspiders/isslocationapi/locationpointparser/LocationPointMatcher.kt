package com.wethejumpingspiders.isslocationapi.locationpointparser

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.wethejumpingspiders.isslocationapi.locationpointparser.room.LocationPointsDatabaseHelper
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class LocationPointMatcher(val context: Context, val databaseHelper: LocationPointsDatabaseHelper) {

    suspend fun getClosestLocationPoint(latitude: Float, longitude: Float): LocationPoint? {
        val countryName = getCountryName(latitude, longitude)
        val locationPointsList: List<LocationPoint>? = getLocationPointsForCountry(countryName)
        val distanceArray = mapDistance(latitude, longitude, locationPointsList)
        return findClosestLocationPoint(distanceArray, locationPointsList)
    }

    private fun getCountryName(latitude: Float, longitude: Float): String {
        val geocoder = Geocoder(context, Locale.getDefault())

        var locationName: String? = null
        val requestLimit = 6
        var requestIndex = 0

        while (locationName.isNullOrEmpty() && requestIndex < requestLimit) {
            try {
                requestIndex++
                val addresses: List<Address> = geocoder.getFromLocation(latitude.toDouble(), longitude.toDouble(), 1)
                if (addresses.size > 0) {
                    val address = addresses[0]
                    locationName = address.countryName
                }
            } catch (e: IOException) {
                locationName = null
            } catch (e: IllegalArgumentException) {
                locationName = null
            }
        }
        return locationName ?: ""
    }

    private suspend fun getLocationPointsForCountry(countryName: String): List<LocationPoint>? {
        return databaseHelper.getAllLocationPointsForCountry(countryName)
    }

    private fun mapDistance(latitude: Float, longitude: Float, locationPoints: List<LocationPoint>?): List<Double>? {
        locationPoints?.let {
            val locationPointsArr: ArrayList<Double> = arrayListOf<Double>()
            for ((index, locationPoint) in locationPoints.withIndex()) {
                locationPointsArr.add(distance(
                    latitude.toDouble(),
                    longitude.toDouble(),
                    locationPoint.latitude.toDouble(),
                    locationPoint.longitude.toDouble()
                ))
            }
            return locationPointsArr
        }
        return null
    }

    private fun findClosestLocationPoint(
        distanceArray: List<Double>?,
        locationPoints: List<LocationPoint>?
    ): LocationPoint? {
        if(distanceArray.isNullOrEmpty() || locationPoints.isNullOrEmpty()) return null
        val minValueIndex = distanceArray.indexOf(distanceArray.min())
        return locationPoints.get(minValueIndex)
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