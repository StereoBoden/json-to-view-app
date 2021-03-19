package com.jbappz.jsontoviews.ui

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.model.AppDescription
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
        initTopRow()
    }

    private fun initTopRow() {
        val redView = RedView(context)
        val yellowView = YellowView(context)

        val topRow = LinearLayout(context)
        topRow.orientation = LinearLayout.HORIZONTAL
        topRow.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        topRow.addView(redView)
        topRow.addView(yellowView)
        container.addView(topRow)
    }

}