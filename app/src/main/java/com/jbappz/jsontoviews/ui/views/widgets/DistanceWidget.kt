package com.jbappz.jsontoviews.ui.views.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.jbappz.jsontoviews.util.Util

class DistanceWidget: AppCompatTextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        layoutParams = Util.getCenterLayoutParams()
        setTextColor(Color.WHITE)
    }
}