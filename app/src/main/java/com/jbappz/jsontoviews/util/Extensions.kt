package com.jbappz.jsontoviews.util

import android.content.res.Resources

object Extensions {
    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}