package com.example.googlemapdirection.response

import com.google.gson.annotations.SerializedName

data class CoordinatesResponse(
    @SerializedName("bbox") val bbox : List<Double>,
    @SerializedName("routes") val routes : List<Routes>,
    @SerializedName("metadata") val metadata : Meta,
)

data class Routes (
    @SerializedName("summary") val summary : Summary,
    @SerializedName("segments") val segments : List<Segments>,
    @SerializedName("bbox") val bbox : List<Double>,
    @SerializedName("geometry") val geometry : String,
    @SerializedName("way_points") val way_points : List<Int>,
    @SerializedName("legs") val legs : List<String>
)
data class Summary (
    @SerializedName("distance") val distance : Double,
    @SerializedName("duration") val duration : Double
)

data class Segments (
    @SerializedName("distance") val distance : Double,
    @SerializedName("duration") val duration : Double,
    @SerializedName("steps") val steps : List<Steps>
)

data class Steps (
    @SerializedName("distance") val distance : Double,
    @SerializedName("duration") val duration : Double,
    @SerializedName("type") val type : Int,
    @SerializedName("instruction") val instruction : String,
    @SerializedName("name") val name : String,
    @SerializedName("way_points") val way_points : List<Int>
)