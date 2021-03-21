package com.jbappz.jsontoviews.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.model.AppDescription
import com.jbappz.jsontoviews.ui.views.*
import com.jbappz.jsontoviews.ui.views.widgets.ClockWidget
import com.jbappz.jsontoviews.ui.views.widgets.ImageWidget
import com.jbappz.jsontoviews.util.Constants.WIDGET_CLOCK
import com.jbappz.jsontoviews.util.Util

/**
 * Factory Class for manipulating the views of the app containing business logic
 * In a real app this would be added as a Singleton Dependency and be unit tested
 * @param context - The app's context
 * @param container - RelativeLayout the app's container layout within MainActivity
 */
class ViewFactory(private val context: Context, private val container: RelativeLayout) {
    private var progressBar: ProgressBar = ProgressBar(
        context,
        null,
        android.R.attr.progressBarStyleSmall
    )

    // Top LinearLayout that will contain Red and Yellow Views
    private val topRow = LinearLayout(context)
    val redView = RedView(context)
    val yellowView = YellowView(context)

    private val greenView = GreenView(context)

    // Bottom LinearLayout that will contain the Blue and Purple Views
    private val bottomRow = LinearLayout(context)
    private val blueView = BlueView(context)
    private val purpleView = PurpleView(context)

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
    }

    /**
     * Initialise the Red Clock Widget
     * Validate the clock properties from the API, only generate if data is valid
     */
    private fun initRedWidget(appDescription: AppDescription?) {
        val redViewConfig = appDescription?.modules?.red ?: return
        val clockExists = (redViewConfig.type == WIDGET_CLOCK && redViewConfig.time_zone.isNotEmpty())
        if(clockExists) {
            val clockWidget = ClockWidget(context)
            clockWidget.setTimeZone(redViewConfig.time_zone)
            redView.addView(clockWidget)
        }
    }

    /**
     * Initialise the Green Image Widget
     * Validate the image URL is not empty, only generate if data is valid
     */
    private fun initGreenWidget(appDescription: AppDescription?) {
        val greenConfig = appDescription?.modules?.green ?: return
        val imageURL = greenConfig.image
        if(imageURL.isNotEmpty()) {
            val imageWidget = ImageWidget(context)
            greenView.addView(progressBar)

            imageWidget.setImage(greenConfig.image) {
                greenView.removeView(progressBar)
            }
            greenView.addView(imageWidget)
        }
    }
}