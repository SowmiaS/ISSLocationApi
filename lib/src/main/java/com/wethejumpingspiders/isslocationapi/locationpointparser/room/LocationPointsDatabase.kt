package com.wethejumpingspiders.isslocationapi.locationpointparser.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint


@Database(entities = arrayOf(LocationPoint::class), version = 1)
abstract class LocationPointsDatabase : RoomDatabase() {

    abstract fun locationPointDao(): LocationPointDao

}

