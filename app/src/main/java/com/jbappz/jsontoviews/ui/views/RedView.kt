package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.util.Constants.RED_HEIGHT
import com.jbappz.jsontoviews.util.Extensions.toPx

/**
 * RedView class representing the Red View within the main container
 * Extending [RelativeLayout] to provide a container for views inside
 */
class RedView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.RED)
        val height = RED_HEIGHT.toPx()
        layoutParams = LinearLayout.LayoutParams(
            0,
            height,
            0.5F
        )
    }
}