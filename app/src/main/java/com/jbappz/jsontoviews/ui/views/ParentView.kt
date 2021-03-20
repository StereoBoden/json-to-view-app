package com.jbappz.jsontoviews.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.RelativeLayout

/**
 * Parent View Class containing Parent [RelativeLayout] inheritance
 * Must provide the custom color to use as the background color
 * Provide a default set of LayoutParams, can be overridden
 */
abstract class ParentView: RelativeLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    abstract val customColor: Int
    private val initColor by lazy { setBackgroundColor(customColor) }

    init {
        initColor
        layoutParams = LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
}