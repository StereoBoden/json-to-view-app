package com.jbappz.jsontoviews.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.model.AppDescription
import com.jbappz.jsontoviews.ui.views.GreenView
import com.jbappz.jsontoviews.ui.views.RedView
import com.jbappz.jsontoviews.ui.views.YellowView


/**
 * Class for manipulating the views of the app
 * @param context - The app's context
 * @param container - RelativeLayout the app's container layout within MainActivity
 */
class ViewDesigner(private val context: Context, private val container: RelativeLayout) {
    private var progressBar: ProgressBar = ProgressBar(
        context,
        null,
        android.R.attr.progressBarStyleSmall
    )

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
        val redViewConfig = appDescription?.modules?.red

        // Initialise Top LinearLayout
        val topRow = LinearLayout(context)
        topRow.id = View.generateViewId()

        initTopRow(topRow)
        initGreenRow(topRow.id)
    }

    /**
     * Initialise Top Row by constructing Red and Yellow Views
     * @param topRow The LinearLayout parent for the Red and Yellow Views
     * Add the parent layout to the container
     */
    private fun initTopRow(topRow: LinearLayout) {
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
     * @param topRowId required as a reference to layout the view under the top row
     * Add the parent layout to the container
     */
    private fun initGreenRow(topRowId: Int) {
        val greenView = GreenView(context)
        val greenParams = greenView.layoutParams as RelativeLayout.LayoutParams
        greenParams.addRule(RelativeLayout.BELOW, topRowId)
        container.addView(greenView)
    }

}