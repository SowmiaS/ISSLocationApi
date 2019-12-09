package com.wethejumpingspiders.isslocationapi.sightingsparser.room

import android.content.Context
import androidx.room.Room
import com.wethejumpingspiders.isslocationapi.api.room.getDatabase
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SightingInfoDatabaseHelper(val context: Context) {


    suspend fun addAllSightingInfos(sightingInfos: List<SightingInfo>) {
        return getDatabase(context).sightingInfoDao().insertAll(sightingInfos)

    }

    suspend fun getSightingInfosForLocation(locationId: Int): List<SightingInfo>? {
        return getDatabase(context).sightingInfoDao().getSightingInfosOfLocationPoint(locationId)
    }
}