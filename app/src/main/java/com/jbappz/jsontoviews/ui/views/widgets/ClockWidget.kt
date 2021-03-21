package com.jbappz.jsontoviews.ui.views.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.jbappz.jsontoviews.util.Util
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

/**
 * Clock Widget class representing an [AppCompatTextView] containing the time
 * Manages its own coroutine context and updates the clock
 */
class ClockWidget: AppCompatTextView, CoroutineScope {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    private var clockActive = false
    private lateinit var clockJob: Job

    // Suppress because locale isnt needed as format is used based solely on the timezone
    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat("HH:mm")

    init {
        layoutParams = Util.getCenterLayoutParams()
    }

    /**
     * Require coroutine context to update the UI time on the main thread
     */
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    /**
     * Start the coroutine job when the view is attached to the window
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        clockActive = true
        clockJob = updateClock()
    }

    /**
     * Cancel the coroutine job when the view is detached to the window
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clockActive = false
        clockJob.cancel()
    }

    fun setTimeZone(tz: String) {
        dateFormat.timeZone = TimeZone.getTimeZone(tz)
    }

    /**
     * Create a coroutine job updating the UI with the time based on the timezone provided
     * Check every second if the minute has turned over
     */
    private fun updateClock(): Job {
        return launch {
            while (clockActive) {
                text = dateFormat.format(
                        Date(System.currentTimeMillis())
                )
                delay(1000)
            }
        }
    }
}