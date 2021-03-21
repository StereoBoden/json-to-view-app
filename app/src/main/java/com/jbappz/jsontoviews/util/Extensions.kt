package com.jbappz.jsontoviews.util

import android.content.res.Resources

object Extensions {
    /**
     * Require true pixel value as per requirement
     * The DP value is a constant and needs converting to pixels to be flexible on all devices
     * as they all have different screen densities
     */
    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    /**
     * Convert Float (im KM) to Miles
     */
    fun Float.toMiles(): Float = (this * 0.62137119F)
}