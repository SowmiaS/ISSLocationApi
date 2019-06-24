package com.wethejumpingspiders.isslocationapi.sightingsparser.room

import android.content.Context
import androidx.room.Room
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo

class SightingInfoDatabaseHelper(context: Context){

    var database : SightingInfoDatabase? = null;

    init {
        getDatabase(context)
    }

    val databaseName : String = "ISSLocator"

    private fun getDatabase(context : Context) {
            database =  Room.databaseBuilder(
            context,
            SightingInfoDatabase::class.java, databaseName
        ).build()
    }

    fun addAllSightingInfos(sightingInfos : List<SightingInfo>){
        database?.sightingInfoDao()?.insertAll(sightingInfos)
    }


    fun getSightingInfosForLocation(locationId: Long) : List<SightingInfo>? {
        return database?.sightingInfoDao()?.getSightingInfosOfLocationPoint(locationId)
    }
}