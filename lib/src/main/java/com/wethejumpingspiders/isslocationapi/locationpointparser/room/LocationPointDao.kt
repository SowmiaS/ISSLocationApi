package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint

@Dao
interface LocationPointDao {

    @Query("SELECT * FROM locationpoint")
    suspend fun getAll(): List<LocationPoint>


    @Query("SELECT * FROM locationpoint WHERE country = :countryName OR country = :countryNameWithUnderScore" )
    suspend fun getLocationPointsOfCountry(countryName : String, countryNameWithUnderScore: String ): List<LocationPoint>

    @Query("SELECT * FROM locationpoint WHERE id = :id" )
    suspend fun getLocationPoint(id : Int): LocationPoint?

    @Insert
     suspend fun insertAll(locationPoint: List<LocationPoint>)

    @Delete
     suspend fun delete(locationPoint: LocationPoint)

    @Query("DELETE FROM locationpoint")
     suspend fun deleteAll()

}