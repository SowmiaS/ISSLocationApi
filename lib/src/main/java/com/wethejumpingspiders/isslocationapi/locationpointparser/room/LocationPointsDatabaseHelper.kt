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
        GlobalScope.launch {
            getDatabase(context).locationPointDao().insertAll(locationPoints)
        }
    }

    suspend fun getAllLocationPoints(): List<LocationPoint>? {
        return GlobalScope.async{
            getDatabase(context).locationPointDao().getAll()
        }.await()
    }

    suspend fun getAllLocationPointsForCountry(countryName: String): List<LocationPoint>? {
        return GlobalScope.async {
            getDatabase(context).locationPointDao().getLocationPointsOfCountry(countryName.trim())
        }.await()
    }

    fun deleteAllLocationPoints() {
        GlobalScope.launch {
            getDatabase(context).locationPointDao().deleteAll()
        }
    }
}