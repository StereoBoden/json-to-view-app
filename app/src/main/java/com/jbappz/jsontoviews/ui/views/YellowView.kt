package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.jbappz.jsontoviews.util.Constants.YELLOW_HEIGHT
import com.jbappz.jsontoviews.util.Extensions.toPx

/**
 * RedView class representing the Yellow View within the main container
 * Extending [RelativeLayout] to provide a container for views inside
 */
class YellowView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.YELLOW)
        val height = YELLOW_HEIGHT.toPx()
        layoutParams = LinearLayout.LayoutParams(
            0,
            height,
            0.5F
        )
    }
}