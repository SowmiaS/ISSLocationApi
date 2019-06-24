package com.wethejumpingspiders.isslocationapi.sightingsparser.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo


@Database(entities = arrayOf(SightingInfo::class), version = 1)
abstract class SightingInfoDatabase : RoomDatabase() {

    abstract fun sightingInfoDao(): SightingInfoDao

}

