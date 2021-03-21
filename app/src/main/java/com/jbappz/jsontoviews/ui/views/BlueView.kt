package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jbappz.jsontoviews.util.Constants.BLUE_PURPLE_DIVIDER

/**
 * Blue View class representing the Blue View within the main container
 */
class BlueView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ParentView(context, attrs, defStyleAttr) {

    override val customColor: Int
        get() = Color.BLUE

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