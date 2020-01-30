package com.wethejumpingspiders.isslocationapi.sightingsparser

import android.content.Context
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.sightingsparser.room.SightingInfoDatabaseHelper
import java.util.*
import kotlin.collections.ArrayList

interface SightingInfoManagerInterface {

    suspend fun getSightingInfo(locationPoint: LocationPoint): List<SightingInfo>?

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
    val downloader = SightingInfoDownloader()


    override suspend fun getSightingInfo(locationPoint: LocationPoint): List<SightingInfo>? {
        var sightingInfos = getSightingInfos(locationPoint)
        sightingInfos?.let {
            databaseHelper.deleteAllSightings()
            databaseHelper.addAllSightingInfos(sightingInfos)
        }
        return databaseHelper.getSightingInfosForLocation(locationPoint.id)
    }


    private suspend fun getSightingInfos(locationPoint: LocationPoint): List<SightingInfo>? {

        val sightingInfoDescList = downloader.getSightingsDesc(locationPoint)
        if (sightingInfoDescList == null) return null
        else if (sightingInfoDescList.isEmpty()) return ArrayList<SightingInfo>()
        else {
            return parser.parseSightingInfos(locationPoint, sightingInfoDescList)
        }
    }

}