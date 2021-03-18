package com.jbappz.jsontoviews.repository

import com.jbappz.jsontoviews.model.*
import com.jbappz.jsontoviews.util.Constants.BLUE
import com.jbappz.jsontoviews.util.Constants.COORDINATES
import com.jbappz.jsontoviews.util.Constants.DATA_URL
import com.jbappz.jsontoviews.util.Constants.GREEN
import com.jbappz.jsontoviews.util.Constants.IMAGE
import com.jbappz.jsontoviews.util.Constants.LATITUDE
import com.jbappz.jsontoviews.util.Constants.LONGITUDE
import com.jbappz.jsontoviews.util.Constants.MODULES
import com.jbappz.jsontoviews.util.Constants.PURPLE
import com.jbappz.jsontoviews.util.Constants.RED
import com.jbappz.jsontoviews.util.Constants.TIME_ZONE
import com.jbappz.jsontoviews.util.Constants.TITLE
import com.jbappz.jsontoviews.util.Constants.TYPE
import com.jbappz.jsontoviews.util.Constants.URL
import com.jbappz.jsontoviews.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

/**
 * Repository for making a network connection to get the JSON Back
 * Using traditional HTTP connections as no third party libaries can be used
 * In a normal application, Retrofit2 with a parsing library such a GSON or Kotlinx would be used
 * In a normal application all the json processing of all the colors wouldnt be needed
 */
// Will be called from a coroutine so can suppress
@Suppress("BlockingMethodInNonBlockingContext")
class AttractionsIORepository {
    private var urlConnection: HttpURLConnection? = null

    /**
     * Async call to API for App Description data
     * @return [Resource] of [AppDescription] - A resource informing the ViewModel of the state of the network call
     * and the underlining data (if any)
     */
    suspend fun getAppDescriptionData(): Resource<AppDescription> {
        val result = withContext(Dispatchers.IO) {
            try {
                val url = URL(DATA_URL)
                val urlConnection = url.openConnection() as HttpURLConnection

                val code: Int = urlConnection.responseCode
                if (code != 200) {
                    return@withContext Resource.error("Error getting data", null)
                }

                val reader = BufferedReader(
                    InputStreamReader(urlConnection.inputStream)
                )

                val jsonString = generateJsonString(reader)
                val appDescription = processJsonString(jsonString)

                return@withContext Resource.success(appDescription)
            } catch (e: Exception) {
                return@withContext Resource.error(e.message ?: "", null)
            } finally {
                urlConnection?.disconnect()
            }
        }
        return result
    }

    /**
     * Get json string from Buffered Reader
     * @return String - The Json as a String
     */
    private fun generateJsonString(reader: BufferedReader): String {
        var line: String? = reader.readLine()
        var result = ""
        while (line != null) {
            result += line
            line = reader.readLine()
        }
        return result
    }

    /**
     * Process the json string
     * @return [AppDescription] - The json as an [AppDescription] object
     */
    private fun processJsonString(json: String): AppDescription {
        // Initial processing, if there's a problem then return an empty AppDescription
        val jsonObject: JSONObject
        val title: String
        val modulesJsonObject: JSONObject
        try {
            jsonObject = JSONObject(json)
            title = jsonObject.getString(TITLE)
            modulesJsonObject = jsonObject.getJSONObject(MODULES)
        }
        catch (e: Exception) {
            return AppDescription()
        }

        val red = processRed(modulesJsonObject)
        val green = processGreen(modulesJsonObject)
        val blue = processBlue(modulesJsonObject)
        val purple = processPurple(modulesJsonObject)
        val modules = Modules(
            red = red,
            green = green,
            blue = blue,
            purple = purple
        )
        return AppDescription(
            title = title,
            modules = modules
        )
    }

    /**
     * Process the json object containing the Red data
     * @return [Red] - The json as a [Red] object
     */
    private fun processRed(modules: JSONObject): Red? {
        return try {
            val redJsonObject = modules.getJSONObject(RED)
            val redType = redJsonObject.getString(TYPE)
            val redTimeZone = redJsonObject.getString(TIME_ZONE)
            Red(redType, redTimeZone)
        }
        catch (e: Exception) {
            null
        }
    }

    /**
     * Process the json object containing the Green data
     * @return [Green] - The json as a [Green] object
     */
    private fun processGreen(modules: JSONObject): Green? {
        return try {
            val greenJsonObject = modules.getJSONObject(GREEN)
            val greenType = greenJsonObject.getString(TYPE)
            val greenImage = greenJsonObject.getString(IMAGE)
            Green(greenType, greenImage)
        }
        catch (e: Exception) {
            null
        }
    }

    /**
     * Process the json object containing the Blue data
     * @return [Blue] - The json as a [Blue] object
     */
    private fun processBlue(modules: JSONObject): Blue? {
        return try {
            val blueJsonObject = modules.getJSONObject(BLUE)
            val blueType = blueJsonObject.getString(TYPE)
            val blueCoordinates = blueJsonObject.getJSONObject(COORDINATES)
            val blueLat = blueCoordinates.getDouble(LATITUDE)
            val blueLon = blueCoordinates.getDouble(LONGITUDE)
            Blue(blueType, Coordinates(blueLat, blueLon))
        }
        catch (e: Exception) {
            null
        }
    }

    /**
     * Process the json object containing the Purple data
     * @return [Purple] - The json as a [Purple] object
     */
    private fun processPurple(modules: JSONObject): Purple? {
        return try {
            val purpleJsonObject = modules.getJSONObject(PURPLE)
            val purpleType = purpleJsonObject.getString(TYPE)
            val purpleTitle = purpleJsonObject.getString(TITLE)
            val purpleURL = purpleJsonObject.getString(URL)
            Purple(purpleType, purpleTitle, purpleURL)
        }
        catch (e: Exception) {
            null
        }
    }
}