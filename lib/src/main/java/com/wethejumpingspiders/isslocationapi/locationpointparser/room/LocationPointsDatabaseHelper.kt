package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import android.content.Context
import androidx.room.Room
import com.wethejumpingspiders.isslocationapi.api.room.ISSLocatorDatabase
import com.wethejumpingspiders.isslocationapi.api.room.getDatabase
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LocationPointsDatabaseHelper(val context: Context) {

    suspend fun addAllLocationPoints(locationPoints: List<LocationPoint>) {
            getDatabase(context).locationPointDao().insertAll(locationPoints)
    }

    suspend fun getAllLocationPoints(): List<LocationPoint>? {
        return getDatabase(context).locationPointDao().getAll()
    }

    suspend fun getLocationPoint(id : Int): LocationPoint? {
        return getDatabase(context).locationPointDao().getLocationPoint(id)
    }

    suspend fun getAllLocationPointsForCountry(countryName: String): List<LocationPoint>? {
        return getDatabase(context).locationPointDao().getLocationPointsOfCountry(countryName.trim())

    }

    suspend fun deleteAllLocationPoints() {
            getDatabase(context).locationPointDao().deleteAll()
    }
}