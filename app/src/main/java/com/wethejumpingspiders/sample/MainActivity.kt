package com.wethejumpingspiders.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wethejumpingspiders.isslocationapi.api.ISSLocator
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPoint
import com.wethejumpingspiders.isslocationapi.locationpointparser.LocationPointParser
import com.wethejumpingspiders.isslocationapi.sightingsparser.ISSSightingDataParser

class MainActivity : AppCompatActivity() , ISSLocator.ISSLocatorInitializationStatus {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ISSLocator(applicationContext).intialise(this)

    }

    override fun onSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
