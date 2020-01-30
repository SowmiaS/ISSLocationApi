package com.wethejumpingspiders.isslocationapi.sightingsparser.room

import androidx.room.*
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo

@Dao
interface SightingInfoDao {

    @Query("SELECT * FROM SightingInfo WHERE locationPointId = :locationPointId")
     suspend fun getSightingInfosOfLocationPoint(locationPointId: Int): List<SightingInfo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertAll(sightingInfos: List<SightingInfo>)

    @Query("DELETE FROM SightingInfo")
    suspend fun deleteAll()

}