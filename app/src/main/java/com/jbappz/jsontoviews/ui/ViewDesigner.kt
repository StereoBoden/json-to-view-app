package com.jbappz.jsontoviews.ui

import android.content.Context
import android.widget.ProgressBar
import android.widget.RelativeLayout

/**
 * Class for manipulating the views of the app
 * @param context - The app's context
 * @param container - RelativeLayout the app's container layout within MainActivity
 */
class ViewDesigner(context: Context, private val container: RelativeLayout) {
    private var progressBar: ProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleSmall)

    init {
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
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
}