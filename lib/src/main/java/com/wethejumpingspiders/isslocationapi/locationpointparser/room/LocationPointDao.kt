package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint

@Dao
interface LocationPointDao {

    @Query("SELECT * FROM locationpoint")
    fun getAll(): List<LocationPoint>


    @Query("SELECT * FROM locationpoint WHERE country = :countryName" )
    fun getLocationPointsOfCountry(countryName : String): List<LocationPoint>

    @Insert
     fun insertAll(locationPoint: List<LocationPoint>)

    @Delete
     fun delete(locationPoint: LocationPoint)

    @Query("DELETE FROM locationpoint")
     fun deleteAll()

}