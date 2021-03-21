package com.jbappz.jsontoviews.ui.views.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlinx.coroutines.*
import java.net.URL
import kotlin.coroutines.CoroutineContext

/**
 * Image Widget class representing an [AppCompatImageView] containing desired image
 * Manages its own coroutine context and updates itself with the specified URL image
 */
class ImageWidget: AppCompatImageView, CoroutineScope {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    )

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    /**
     * Make a network call to get the image
     * Ensure the UI is updated on the Main Thread after the image has been retrieved
     * @param imageUrl the URL of the desired image
     * @param onDownloadFinishedListener a listener to inform when the image has been successfully downloaded
     */
    fun setImage(imageUrl: String, onDownloadFinishedListener: (() -> Unit)? = null) {
        launch {
            var bmp: Bitmap? = null
            val imageGet = async(Dispatchers.IO) {
                val url = URL(imageUrl)
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            }
            imageGet.await()

            // Update the UI on the main thread
            async(Dispatchers.Main) {
                setImageBitmap(bmp)
                onDownloadFinishedListener?.invoke()
            }
        }
    }
}