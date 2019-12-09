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
    val id: Int = 0,
    val date: String, val time: String, val duration: String, val maxElevation: String,
    val approach: String, val departure: String, val locationPointId: Int) {

    fun getApproachData() : AppearanceData{
        val approachList: List<String> = approach.trim().split(" ")
        val approachDirection: DIRECTION = DIRECTION.valueOf(approachList.get(2).trim().toString())
        val degree = approachDirection.degree
        return AppearanceData(degree, approachDirection, approach)
    }

    fun getDepartureData() : AppearanceData{
        val departureList: List<String> = departure.trim().split(" ")
        val departureDirection: DIRECTION = DIRECTION.valueOf(departureList.get(2).trim().toString())
        val degree = departureDirection.degree
        return AppearanceData(degree, departureDirection, departure)
    }

}

data class AppearanceData(val degree : Int, val direction: DIRECTION , val string: String)

enum class DIRECTION(val degree: Int) {
    N(0),

    NNE(22),
    NE(45),
    ENE(67),

    E(90),

    ESE(112),
    SE(135),
    SSE(157),

    S(180),

    SSW(202),
    SW(225),
    WSW(247),

    W(270),

    WNW(292),
    NNW(327),
    NW(315)
}