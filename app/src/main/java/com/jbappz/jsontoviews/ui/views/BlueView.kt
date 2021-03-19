package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.util.Constants
import com.jbappz.jsontoviews.util.Constants.BLUE_PURPLE_DIVIDER

/**
 * Blue View class representing the Red View within the main container
 * Extending [RelativeLayout] to provide a container for views inside
 */
class BlueView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.BLUE)
    }

    /**
     * Divide the height by [BLUE_PURPLE_DIVIDER] as per requirement
     * Force a 1F weight to ensure this view takes up as much space as possible
     */
    fun setCustomLayoutParams(height: Int) {
        val newHeight = height / BLUE_PURPLE_DIVIDER
        layoutParams = LinearLayout.LayoutParams(
            0,
            newHeight,
            1F
        )
    }
}