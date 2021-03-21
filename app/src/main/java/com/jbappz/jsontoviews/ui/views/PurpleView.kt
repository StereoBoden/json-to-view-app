package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jbappz.jsontoviews.util.Constants.BLUE_PURPLE_DIVIDER

/**
 * Purple View class representing the Red View within the main container
 */
class PurpleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ParentView(context, attrs, defStyleAttr) {

    override val customColor: Int
        get() = Color.MAGENTA

    /**
     * Generate ratio using [BLUE_PURPLE_DIVIDER] as per requirement
     * Maintain 1:1 aspect ratio of width and height
     */
    fun setCustomLayoutParams(size: Int) {
        val ratioSize = size / BLUE_PURPLE_DIVIDER
        layoutParams = LinearLayout.LayoutParams(
                ratioSize,
                ratioSize
        )
    }
}