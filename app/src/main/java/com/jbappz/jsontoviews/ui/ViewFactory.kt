package com.jbappz.jsontoviews.ui

import android.content.Context
import android.location.Location
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatButton
import com.jbappz.jsontoviews.model.AppDescription
import com.jbappz.jsontoviews.ui.views.*
import com.jbappz.jsontoviews.ui.views.widgets.ClockWidget
import com.jbappz.jsontoviews.ui.views.widgets.DistanceWidget
import com.jbappz.jsontoviews.ui.views.widgets.ImageWidget
import com.jbappz.jsontoviews.util.Constants.LOCATION_UNKNOWN
import com.jbappz.jsontoviews.util.Constants.WIDGET_CLOCK
import com.jbappz.jsontoviews.util.Util

/**
 * Factory Class for manipulating the views of the app containing business logic
 * In a real app this would be added as a Singleton Dependency and be unit tested
 * @param context - The app's context
 * @param container - RelativeLayout the app's container layout within MainActivity
 */
class ViewFactory(private val context: Context, private val container: RelativeLayout) {
    var onLocationCoordsValid: (() -> Unit)? = null

    private var progressBar: ProgressBar = ProgressBar(
        context,
        null,
        android.R.attr.progressBarStyleSmall
    )

    // Top LinearLayout that will contain Red and Yellow Views
    private val topRow = LinearLayout(context)
    private val redView = RedView(context)
    private val yellowView = YellowView(context)

    private val greenView = GreenView(context)

    // Bottom LinearLayout that will contain the Blue and Purple Views
    private val bottomRow = LinearLayout(context)
    private val blueView = BlueView(context)
    private val purpleView = PurpleView(context)

    // Lat and Lon from config
    private var configLat = -1.0
    private var configLon = -1.0

    // Connect any errors with the data
    private var errorString = ""

    init {
       initProgressBar()
    }

    fun addProgressBar() {
        container.addView(progressBar)
    }

    fun removeProgressBar() {
        container.removeView(progressBar)
    }

    fun updateUI(appDescription: AppDescription?) {
        topRow.id = View.generateViewId()
        bottomRow.id = View.generateViewId()

        initTopRow()
        initGreenRow()
        initBottomRow()

        initWidgets(appDescription)
        if (errorString.isNotEmpty()) {
            Util.errorDialog(context, errorString)
        }
    }

    /**
     * Update the blue location (in Miles) once the device's lat and lon has come back
     * @param lat the latitude of the device
     * @param lon the longitude of the device
     */
    fun updateBlueLocationView(lat: Double, lon: Double) {
        val distanceWidget = DistanceWidget(context)
        try {
            val milesResult = if (
                lat != -1.0 &&
                lon != -1.0 &&
                configLat != -1.0 &&
                configLon != -1.0) {
                val distanceInMiles = getDistanceInMiles(lat, lon)
                "$distanceInMiles Miles"
            }
            else {
                LOCATION_UNKNOWN
            }

            distanceWidget.text = milesResult
        }
        catch (e: Exception) {
            distanceWidget.text = LOCATION_UNKNOWN
        }
        blueView.removeAllViews()
        blueView.addView(distanceWidget)
    }

    /**
     * Initialise the progress bar to indicate a loading state
     * Align in the center of the view
     */
    private fun initProgressBar() {
        progressBar.layoutParams = Util.getCenterLayoutParams()
    }

    /**
     * Initialise Top Row by constructing Red and Yellow Views
     * Add the parent layout to the container
     */
    private fun initTopRow() {
        topRow.orientation = LinearLayout.HORIZONTAL
        topRow.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        topRow.addView(redView)
        topRow.addView(yellowView)
        container.addView(topRow)
    }

    /**
     * Initialise The Green View
     * Monitor the height of the view as a callback to update Blue and Purple
     * Add the parent layout to the container
     */
    private fun initGreenRow() {
        val greenParams = greenView.layoutParams as RelativeLayout.LayoutParams
        greenParams.addRule(RelativeLayout.BELOW, topRow.id)
        greenParams.addRule(RelativeLayout.ABOVE, bottomRow.id)
        container.addView(greenView)
        greenView.onDrawListener = { greenHeight ->
            blueView.setCustomLayoutParams(greenHeight)
            purpleView.setCustomLayoutParams(greenHeight)
        }
    }

    /**
     * Initialise Bottom Row by constructing Blue and Purple Views
     * Add the parent layout to the container
     */
    private fun initBottomRow() {
        bottomRow.orientation = LinearLayout.HORIZONTAL
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        bottomRow.layoutParams = params

        bottomRow.addView(blueView)
        bottomRow.addView(purpleView)
        container.addView(bottomRow)
    }

    /**
     * Use config to draw views inside color layouts
     */
    private fun initWidgets(appDescription: AppDescription?) {
        initRedWidget(appDescription)
        initGreenWidget(appDescription)
        initBlueWidget(appDescription)
        initPurpleWidget(appDescription)
    }

    /**
     * Initialise the Red Clock Widget
     * Validate the clock properties from the API, only generate if data is valid
     */
    private fun initRedWidget(appDescription: AppDescription?) {
        val redViewConfig = appDescription?.modules?.red
        if(redViewConfig == null) {
            errorString += "No Red Json, "
            return
        }

        val clockExists = (redViewConfig.type == WIDGET_CLOCK && redViewConfig.time_zone.isNotEmpty())
        if(clockExists) {
            val clockWidget = ClockWidget(context)
            clockWidget.setTimeZone(redViewConfig.time_zone)
            redView.addView(clockWidget)
        }
        else {
            errorString += "Problem with Red Json, "
        }
    }

    /**
     * Initialise the Green Image Widget
     * Validate the image URL is not empty, only generate if data is valid
     */
    private fun initGreenWidget(appDescription: AppDescription?) {
        val greenConfig = appDescription?.modules?.green
        if(greenConfig == null) {
            errorString += "No Green Json, "
            return
        }

        val imageURL = greenConfig.image
        if(imageURL.isNotEmpty()) {
            val imageWidget = ImageWidget(context)
            greenView.addView(progressBar)

            imageWidget.setImage(greenConfig.image) {
                greenView.removeView(progressBar)
            }
            greenView.addView(imageWidget)
        }
        else {
            errorString += "Problem with Green Json, "
        }
    }

    /**
     * Add the distance widget to the Blue view based on if the blue config is available
     */
    private fun initBlueWidget(appDescription: AppDescription?) {
        val blueConfig = appDescription?.modules?.blue
        if(blueConfig == null) {
            errorString += "No Blue Json, "
        }
        val coords = blueConfig?.coordinates
        if (coords?.valid == true) {
            configLat = coords.latitude
            configLon = coords.longitude
            onLocationCoordsValid?.invoke()
        }
        else {
            errorString += "Problem with Blue Json, "
        }
    }

    /**
     * Create an AppcompatButton and add it to the purple view
     * Initialise text and click listener based on config
     */
    private fun initPurpleWidget(appDescription: AppDescription?) {
        val purpleConfig = appDescription?.modules?.purple
        if(purpleConfig == null) {
            errorString += "No Purple Json, "
        }

        val buttonTitle = purpleConfig?.title ?: ""
        val url = purpleConfig?.url ?: ""
        if(buttonTitle.isNotEmpty() && url.isNotEmpty()) {
            val button = AppCompatButton(context)
            button.text = buttonTitle
            button.setOnClickListener {
                Util.openBrowser(context, url)
            }
            button.layoutParams = Util.getCenterLayoutParams()
            purpleView.addView(button)
        }
        else {
            errorString += "Problem with Purple Json, "
        }
    }

    /**
     * Use [Location] library to calculate distance, it is returned in KM so convert to Miles
     * @param lat the latitude to calculate with
     * @param lon the longitude to calculate with
     */
    private fun getDistanceInMiles(lat: Double, lon: Double): Double =
        Util.getDistance(lat, lon, configLat, configLon)

}