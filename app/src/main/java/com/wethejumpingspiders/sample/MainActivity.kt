package com.wethejumpingspiders.sample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wethejumpingspiders.isslocationapi.api.ISSLocator
import com.wethejumpingspiders.isslocationapi.api.ISSLocatorInitializationError
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPointParser
import com.wethejumpingspiders.isslocationapi.sightingsparser.ISSSightingDataParser
import com.wethejumpingspiders.isslocationapi.sightingsparser.SightingInfo
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progress = findViewById<ProgressBar>(R.id.progress)
        val matchedLPTextview = findViewById<TextView>(R.id.matchedlocationpoint)


        progress.visibility = View.VISIBLE
        val locator = ISSLocator(applicationContext)
        GlobalScope.launch(Dispatchers.IO) {
            locator.intialise()
            val locationPoints = locator.getAllLocationPoints()
            locationPoints?.get(0)?.country
            withContext(Dispatchers.Main){ Toast.makeText(applicationContext, "Number of Location Points ", Toast.LENGTH_LONG).show() }
            val matchedLocationPoint = locator.getMatchedLocationPoint(11.0168f, 76.9558f)
            withContext(Dispatchers.Main){matchedLPTextview.setText(matchedLocationPoint?.city)}

            val sightingInfos: List<SightingInfo>? =
                matchedLocationPoint?.let { locator.getSightingInformation(it) }
            withContext(Dispatchers.Main){ Toast.makeText(applicationContext, " no of sightings ${sightingInfos?.size}", Toast.LENGTH_LONG).show() }

            if (sightingInfos == null) {
                withContext(Dispatchers.Main){ Toast.makeText(applicationContext, "Error retriving SIGHTING INFO...", Toast.LENGTH_LONG).show() }
                Log.d("TAG","Error retriving SIGHTING INFO...")
            } else if (sightingInfos.isEmpty()) {
                withContext(Dispatchers.Main){ Toast.makeText(applicationContext, "NO SIGHTING INFO...", Toast.LENGTH_LONG).show() }
                Log.d("TAG","NO SIGHTING INFO...")
            }else {

                    for (sightingInfo in sightingInfos) {
                        withContext(Dispatchers.Main){ Toast.makeText(applicationContext, "${sightingInfo}", Toast.LENGTH_LONG).show() }
                        Log.d("TAG","$sightingInfo")
                        Log.d("TAG","A APPEARS ${sightingInfo.getApproachData().degree} ,${sightingInfo.getApproachData().direction} , ${sightingInfo.getApproachData().string}")
                        Log.d("TAG","A DISAAPPEARS ${sightingInfo.getDepartureData().degree} ,${sightingInfo.getDepartureData().direction} , ${sightingInfo.getDepartureData().string}")
                        Log.d("TAG","A MAX ELEVATION ${sightingInfo.maxElevation}")
                    }
                val location = sightingInfos?.get(0)?.let { locator.getLocationForSighting(it) }
                Log.d("TAG","A APPEARS $location")
                withContext(Dispatchers.Main){ Toast.makeText(applicationContext, "$location", Toast.LENGTH_LONG).show() }
            }
        }
    }

    suspend fun ISSLocator.getLocationForSighting(info : SightingInfo) : String{
        val locationPoint = getLocationPoint(info.locationPointId)
        return "${locationPoint?.city}, ${locationPoint?.country}"
    }

}
