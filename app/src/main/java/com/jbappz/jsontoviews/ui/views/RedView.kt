package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import com.jbappz.jsontoviews.util.Constants.RED_HEIGHT
import com.jbappz.jsontoviews.util.Extensions.toPx

/**
 * RedView class representing the Red View within the main container
 */
class RedView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ParentView(context, attrs, defStyleAttr) {

    override val customColor: Int
        get() = Color.RED

    init {
        val height = RED_HEIGHT.toPx()
        layoutParams = LinearLayout.LayoutParams(
            0,
            height,
            0.5F
        )
    }
}