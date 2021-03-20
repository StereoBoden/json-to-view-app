package com.jbappz.jsontoviews.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.model.AppDescription
import com.jbappz.jsontoviews.ui.views.*

/**
 * Class for manipulating the views of the app
 * @param context - The app's context
 * @param container - RelativeLayout the app's container layout within MainActivity
 */
class ViewFactory(private val context: Context, private val container: RelativeLayout) {
    private var progressBar: ProgressBar = ProgressBar(
        context,
        null,
        android.R.attr.progressBarStyleSmall
    )

    private val topRow = LinearLayout(context)
    private val greenView = GreenView(context)
    private val bottomRow = LinearLayout(context)
    private val blueView = BlueView(context)
    private val purpleView = PurpleView(context)

    init {
        val params = RelativeLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
        progressBar.layoutParams = params
    }

    fun addProgressBar() {
        container.addView(progressBar)
    }

    fun removeProgressBar() {
        container.removeView(progressBar)
    }

    fun updateUI(appDescription: AppDescription?) {
        // TODO: Use config to draw views inside color layouts
        val redViewConfig = appDescription?.modules?.red

        topRow.id = View.generateViewId()
        bottomRow.id = View.generateViewId()

        initTopRow()
        initGreenRow()
        initBottomRow()
    }

    /**
     * Initialise Top Row by constructing Red and Yellow Views
     * Add the parent layout to the container
     */
    private fun initTopRow() {
        val redView = RedView(context)
        val yellowView = YellowView(context)

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
        greenView.setOnMeasureUpdate { greenHeight ->
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
}