package com.jbappz.jsontoviews.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.R
import java.lang.Exception

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
        catch (e : Exception) {
            return
        }

        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(browserIntent)
    }
}