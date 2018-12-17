package com.rubenexposito.flightsmap.common

import com.google.gson.GsonBuilder
import com.rubenexposito.flightsmap.data.network.ArrayAdapterFactory
import java.io.File

fun <T> File.fromJson(type: Class<T>): T =
    GsonBuilder().registerTypeAdapterFactory(ArrayAdapterFactory()).create().fromJson(this.readText(), type)