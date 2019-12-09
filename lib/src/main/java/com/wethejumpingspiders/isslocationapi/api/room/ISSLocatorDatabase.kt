package com.wethejumpingspiders.isslocationapi.api.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.locationpointparser.room.LocationPointDao
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo
import com.wethejumpingspiders.isslocationapi.sightingsparser.room.SightingInfoDao

@Database(entities = arrayOf(LocationPoint::class, SightingInfo::class), version = 2)
abstract class ISSLocatorDatabase : RoomDatabase() {

    abstract fun locationPointDao(): LocationPointDao

    abstract fun sightingInfoDao(): SightingInfoDao

}

val databaseName : String = "ISSLocator"

suspend fun getDatabase(context: Context) : ISSLocatorDatabase {
    database = database ?: Room.databaseBuilder(
        context,
        ISSLocatorDatabase::class.java, databaseName
    ).fallbackToDestructiveMigration().build()
    return database as ISSLocatorDatabase
}

var database: ISSLocatorDatabase? = null
