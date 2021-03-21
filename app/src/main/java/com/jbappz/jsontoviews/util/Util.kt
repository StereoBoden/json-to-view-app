package com.jbappz.jsontoviews.util

import android.app.AlertDialog
import android.content.Context
import android.widget.FrameLayout
import android.widget.RelativeLayout

object Util {
    fun errorDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage("There was an error")
            .setPositiveButton("OK") { d, _ ->
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
}