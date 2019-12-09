package com.wethejumpingspiders.isslocationapi.sightingsparser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo

@Dao
interface SightingInfoDao {

    @Query("SELECT * FROM SightingInfo WHERE locationPointId = :locationPointId")
     suspend fun getSightingInfosOfLocationPoint(locationPointId: Int): List<SightingInfo>

    @Insert
     suspend fun insertAll(sightingInfos: List<SightingInfo>)

    @Delete
     suspend fun deleteAll(sightingInfos: List<SightingInfo>)

}