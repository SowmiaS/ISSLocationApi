package com.wethejumpingspiders.isslocationapi.sightingsparser

import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
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
    fun onSightingInfoChanged(sightingInfos: ArrayList<SightingInfo>)

    /**
     * This method is called when any failure occurs.
     */
    fun onFailure(errorMsg: String)
}

class SightingInfoManager : SightingInfoManagerInterface {

    override fun getSightingInfo(locationPoint: LocationPoint, listener: OnSightingInfoResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}