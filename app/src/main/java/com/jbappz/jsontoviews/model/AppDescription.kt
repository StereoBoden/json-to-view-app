package com.jbappz.jsontoviews.model

data class AppDescription(
    val title: String = "",
    val modules: Modules? = null
)

data class Modules (
    val red: Red? = null,
    val green: Green? = null,
    val blue: Blue? = null,
    val purple: Purple? = null
)

data class Red (
    val type: String = "",
    val time_zone: String = ""
)

data class Green (
    val type: String = "",
    val image: String = ""
)

data class Blue (
    val type: String = "",
    val coordinates: Coordinates? = null
)

data class Coordinates(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    val valid = latitude != 0.0 && longitude != 0.0
}

data class Purple (
    val type: String = "",
    val title: String = "",
    val url: String = ""
)

