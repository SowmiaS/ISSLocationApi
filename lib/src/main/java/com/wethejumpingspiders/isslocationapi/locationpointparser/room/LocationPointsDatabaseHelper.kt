package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import android.content.Context
import androidx.room.Room
import com.wethejumpingspiders.isslocationapi.api.room.ISSLocatorDatabase
import com.wethejumpingspiders.isslocationapi.api.room.getDatabase
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint

class LocationPointsDatabaseHelper(val context: Context) {

    fun addAllLocationPoints(locationPoints: List<LocationPoint>) {
        getDatabase(context).locationPointDao().insertAll(locationPoints)
    }

    fun getAllLocationPoints(): List<LocationPoint>? {
        return getDatabase(context).locationPointDao().getAll()
    }

    fun getAllLocationPoints(countryName: String): List<LocationPoint>? {
        return getDatabase(context).locationPointDao().getLocationPointsOfCountry(countryName)
    }

    fun deleteAllLocationPoints() {
        getDatabase(context).locationPointDao().deleteAll()
    }
}