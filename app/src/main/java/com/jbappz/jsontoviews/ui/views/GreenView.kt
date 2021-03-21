package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet

/**
 * Green View class representing the Green View within the main container
 */
class GreenView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ParentView(context, attrs, defStyleAttr) {

    override val customColor: Int
        get() = Color.GREEN

    /**
     * Listener to monitor and pass back the canvas height
     * This is a height param is a dependency that the Blue and Purple views require
     */
    var onDrawListener: ((Int) -> Unit)? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        onDrawListener?.invoke(height)
    }
}