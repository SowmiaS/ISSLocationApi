package com.wethejumpingspiders.isslocationapi

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPointParser
import com.wethejumpingspiders.isslocationapi.sightingsparser.ISSSightingDataParser

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val locationPointParser  : LocationPointParser = LocationPointParser()
        locationPointParser.getLocationPointsJS()

        val parser : ISSSightingDataParser =
            ISSSightingDataParser()
        parser.getSightings(LocationPoint("","","","India", "None","Coimbatore"));

    }
}
