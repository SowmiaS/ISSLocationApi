package com.wethejumpingspiders.isslocationapi.locationpointparser


interface  LocationPointManagerInterface{

    fun downloadLocationPoints() : ArrayList<LocationPoint>
    fun storeLocationPointsInRoom( locationPoints: ArrayList<LocationPoint>)
    fun getAllLocationPoints()
    fun getMatchedLocationPoints()

}


class LocationPointManager : LocationPointManagerInterface{


    override fun downloadLocationPoints(): ArrayList<LocationPoint> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun storeLocationPointsInRoom(locationPoints: ArrayList<LocationPoint>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllLocationPoints() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMatchedLocationPoints() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
