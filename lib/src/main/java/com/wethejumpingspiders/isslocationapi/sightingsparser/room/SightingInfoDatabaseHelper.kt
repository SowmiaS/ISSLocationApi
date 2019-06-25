package com.wethejumpingspiders.isslocationapi.sightingsparser.room

import android.content.Context
import androidx.room.Room
import com.wethejumpingspiders.isslocationapi.api.room.getDatabase
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo

class SightingInfoDatabaseHelper(val context: Context) {


    fun addAllSightingInfos(sightingInfos: List<SightingInfo>) {
        getDatabase(context).sightingInfoDao().insertAll(sightingInfos)
    }


    fun getSightingInfosForLocation(locationId: Long): List<SightingInfo>? {
        return getDatabase(context).sightingInfoDao().getSightingInfosOfLocationPoint(locationId)
    }
}