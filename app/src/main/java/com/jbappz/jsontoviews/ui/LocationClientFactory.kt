package com.jbappz.jsontoviews.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.*

/**
 * Location wrapper factory class to monitor location using [LocationServices]
 * SuppressLint because the check is done at activity level
 */
@SuppressLint("MissingPermission")
class LocationClientFactory(context: Context) {
    // Listener to return lat and lon of device
    var locationListener: ((Double, Double) -> Unit?)? = null

    init {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations){
                    val lat = location?.latitude ?: -1.0
                    val lon = location?.longitude ?: -1.0
                    locationListener?.invoke(lat, lon)
                }

            }
        }

        val request = LocationRequest.create()
        request.interval = 3000

        fusedLocationClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper())
    }
}