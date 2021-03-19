package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout

/**
 * Green View class representing the Green View within the main container
 * Extending [RelativeLayout] to provide a container for views inside
 */
class GreenView : RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.GREEN)
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * Listener to monitor and pass back the canvas height
     * This is a height param is a dependency that the Blue and Purple views require
     */
    var onDrawListener: ((Int) -> Unit)? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        onDrawListener?.invoke(height)
    }

    fun setOnMeasureUpdate(listener: (Int) -> Unit) {
        onDrawListener = listener
    }
}