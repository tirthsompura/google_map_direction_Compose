package com.example.googlemapdirection.response

import com.google.gson.annotations.SerializedName


data class Meta(
    @SerializedName("attribution") val attribution : String,
    @SerializedName("service") val service : String,
    @SerializedName("timestamp") val timestamp : Long,
    @SerializedName("query") val query : Query,
    @SerializedName("engine") val engine : Engine,
)

data class Query (
    @SerializedName("coordinates") val coordinates : List<List<Double>>,
    @SerializedName("profile") val profile : String,
    @SerializedName("format") val format : String
)

data class Engine (
    @SerializedName("version") val version : String,
    @SerializedName("build_date") val build_date : String,
    @SerializedName("graph_date") val graph_date : String
)