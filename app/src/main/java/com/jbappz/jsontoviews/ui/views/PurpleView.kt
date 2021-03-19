package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.util.Constants.BLUE_PURPLE_DIVIDER

/**
 * Purple View class representing the Red View within the main container
 * Extending [RelativeLayout] to provide a container for views inside
 */
class PurpleView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.MAGENTA)
    }

    /**
     * Generate ratio using [BLUE_PURPLE_DIVIDER] as per requirement
     * Maintain 1:1 aspect ratio of width and height
     */
    fun setCustomLayoutParams(size: Int) {
        val ratio = size / BLUE_PURPLE_DIVIDER
        layoutParams = LinearLayout.LayoutParams(
            ratio,
            ratio
        )
    }
}