package com.wethejumpingspiders.isslocationapi.locationpointparser

class LocationPointParser {

    private val delimeter = "],["
    private val prefix = "var addressPoints = [["
    private val suffix = "]];"

    fun parseLocationPoints(input: String): List<LocationPoint> {
        return input.removePrefix(prefix).removeSuffix(suffix).split(delimeter)
            .map { it -> getLocationPoint(it.trim()) }
    }


    private fun getLocationPoint(text: String): LocationPoint {

        val regex = getRegexString().toRegex()
        return regex.matchEntire(text)
            ?.destructured
            ?.let { (name, latitude, longitude, requestId1, requestId2, requestId3) ->
                LocationPoint(
                    name,
                    latitude,
                    longitude,
                    requestId1,
                    requestId2,
                    requestId3
                )
            }
            ?: throw IllegalArgumentException()
    }


    private fun getRegexString(): String {
        val addressRegex = "(^'[A-Za-z\\\\s]+, [A-Za-z\\\\s]+')"
        val locationNameRegex = "('[A-Za-z\\\\s]+')"
        val decimalRegex = "(-*\\d*\\.\\d+)"
        return "$addressRegex,$decimalRegex,$decimalRegex,$locationNameRegex,$locationNameRegex,$locationNameRegex";
    }

    /***
     * This method is just used for testing for time being. Need to be removed later
     */
    private fun testLocationPointsParser() {
        val locationpoint = "'Kabul, Afghanistan',34.5553494,69.2074860,'None','Afghanistan','Kabul'"
        val locationpoint2 = "'Tirana, Albania',41.3275459,19.8186982,'None','Albania','Tirana'"
        val locationpoint3 = "'Algiers, Algeria',36.7537703,3.0587927,'None','Algeria','Algiers'"

        val values = prefix + locationpoint + delimeter + locationpoint2 + delimeter + locationpoint3 + suffix
        parseLocationPoints(values)
    }
}

data class LocationPoint(
    val name: String,
    val latitude: String,
    val longitude: String,
    val requestid1: String,
    val requestid2: String,
    val requestid3: String
)


