package com.jbappz.jsontoviews.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.R
import java.math.BigDecimal
import java.math.RoundingMode

object Util {
    fun errorDialog(context: Context, errorMessageResId: Int) {
        AlertDialog.Builder(context)
            .setTitle(context.resources.getString(R.string.error))
            .setMessage(context.resources.getString(errorMessageResId))
            .setPositiveButton(context.resources.getString(R.string.ok)) { d, _ ->
                d.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun errorDialog(context: Context, errorMessage: String) {
        AlertDialog.Builder(context)
            .setTitle(context.resources.getString(R.string.error))
            .setMessage(errorMessage)
            .setPositiveButton(context.resources.getString(R.string.ok)) { d, _ ->
                d.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    fun getCenterLayoutParams(): RelativeLayout.LayoutParams {
        val params = RelativeLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        return params
    }

    fun openBrowser(context: Context, url: String) {
        val uri = try {
            Uri.parse(url)
        }
        catch (e: Exception) {
            return
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(browserIntent)
    }

    /**
     * Gets the distance between two locations
     * @param location1Lat the latitude of Location 1
     * @param location1Lon the longitude of Location 1
     * @param location2Lat the latitude of Location 2
     * @param location2Lon the longitude of Location 2
     * @return The distance in Miles rounded to 2 decimal places
     */
    fun getDistance(
        location1Lat: Double, location1Lon: Double,
        location2Lat: Double, location2Lon: Double
    ): Double {
        val loc1 = Location("")
        loc1.latitude = location1Lat
        loc1.longitude = location1Lon

        val loc2 = Location("")
        loc2.latitude = location2Lat
        loc2.longitude = location2Lon

        val distanceInMeters = loc1.distanceTo(loc2)
        val distanceInKM = (distanceInMeters / 1000).toDouble()

        // Convert to Miles
        val distanceInMiles = distanceInKM * 0.62137119F

        return BigDecimal(distanceInMiles).setScale(2, RoundingMode.HALF_UP).toDouble()
    }
}