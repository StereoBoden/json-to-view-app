package com.jbappz.jsontoviews.ui

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * RedView class representing the Red View within the main container
 * Extending [RelativeLayout] to provide a container for views inside
 */
class RedView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setBackgroundColor(Color.RED)
    }

    // TODO: Calculate heights
    fun toPx(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
}