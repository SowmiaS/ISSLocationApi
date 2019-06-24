package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import android.content.Context
import androidx.room.Room
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint

class LocationPointsDatabaseHelper(context: Context){

    var database : LocationPointsDatabase? = null;

    init {
        getDatabase(context)
    }

    val databaseName : String = "ISSLocator"

    private fun getDatabase(context : Context) {
            database =  Room.databaseBuilder(
            context,
            LocationPointsDatabase::class.java, databaseName
        ).build()
    }

    fun addAllLocationPoints(locationPoints : List<LocationPoint>){
        database?.locationPointDao()?.insertAll(locationPoints)
    }

    fun getAllLocationPoints() : List<LocationPoint>? {
        return database?.locationPointDao()?.getAll()
    }

    fun getAllLocationPoints(countryName: String) : List<LocationPoint>? {
        return database?.locationPointDao()?.getLocationPointsOfCountry(countryName)
    }

    fun deleteAllLocationPoints(){
        database?.locationPointDao()?.deleteAll()
    }
}