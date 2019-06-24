package com.wethejumpingspiders.isslocationapi.sightingsparser

import android.content.Context
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.room.SightingInfoDatabaseHelper
import java.util.*

interface SightingInfoManagerInterface {

    fun getSightingInfo(locationPoint: LocationPoint, listener: OnSightingInfoResponse)

}

/**
 * This class is responsible for communcating the sighting information to the listener.
 */
interface OnSightingInfoResponse {
    /**
     * This method is called when sighting information is retrieved or modified
     */
    fun onSightingInfoChanged(sightingInfos: List<SightingInfo>)

    /**
     * This method is called when any failure occurs.
     */
    fun onFailure(errorMsg: String)
}

class SightingInfoManager(val context: Context) : SightingInfoManagerInterface {

    val databaseHelper = SightingInfoDatabaseHelper(context)
    val parser = ISSSightingDataParser()


    override fun getSightingInfo(locationPoint: LocationPoint, listener: OnSightingInfoResponse) {
        databaseHelper.getSightingInfosForLocation(locationPoint.id)?.let {
            val sightingInfos = parser.getSightingInfos(locationPoint)
            listener.onSightingInfoChanged(sightingInfos)
            databaseHelper.addAllSightingInfos(sightingInfos)
        }
    }

}