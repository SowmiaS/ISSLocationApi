package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint

@Dao
interface LocationPointDao {

    @Query("SELECT * FROM LocationPoint")
    fun getAll(): List<LocationPoint>

    @Query("SELECT * FROM LocationPoint WHERE country= :countryName" )
    fun getLocationPointsOfCountry(countryName : String): List<LocationPoint>

    @Insert
    fun insertAll(vararg locationPoint: List<LocationPoint>)

    @Delete
    fun delete(locationPoint: LocationPoint)

    @Query("DELETE FROM LocationPoint")
    fun deleteAll()

}