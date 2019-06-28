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
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val progress = findViewById<ProgressBar>(R.id.progress)
        val matchedLPTextview = findViewById<TextView>(R.id.matchedlocationpoint)


        progress.visibility = View.VISIBLE
        val locator = ISSLocator(applicationContext)
        GlobalScope.launch(Dispatchers.Main) {
            locator.intialise()
            val locationPoints  = async{ locator.getAllLocationPoints() }
            locationPoints.await()?.get(0)?.country
            Toast.makeText(applicationContext, "Number of Location Points ", Toast.LENGTH_LONG).show()


            val matchedLocationPoint = async { locator.getMatchedLocationPoint(11.2746f,77.5826f) }
            matchedLPTextview.setText(matchedLocationPoint.await()?.city)


        }



    }


}
