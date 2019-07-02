package com.wethejumpingspiders.isslocationapi.sightingsparser

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint

@Entity(foreignKeys = arrayOf(ForeignKey(
    entity = LocationPoint::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("locationPointId"))
)
)
data class SightingInfo(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val date: String, val time: String, val duration: String, val maxElevation: String,
    val approach: String, val departure: String, val locationPointId: Int) {


}
