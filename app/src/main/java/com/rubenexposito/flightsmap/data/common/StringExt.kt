package com.rubenexposito.flightsmap.data.common

import java.text.SimpleDateFormat
import java.util.*

fun String.toHoursMinutes() : String {
    val date = SimpleDateFormat("'PT'HH'H'mm'M'", Locale.getDefault()).parse(this)
    return SimpleDateFormat("HH'h 'mm'min'", Locale.getDefault()).format(date   )

//    val lower = removeRange(0,2).toLowerCase()
//
//    return "${lower.substring(0, 3)} ${lower.substring(3, 5)}in"
}

fun String.toHours() : String {
    val date = SimpleDateFormat("YYYY-MM-dd'T'HH:mm", Locale.getDefault()).parse(this)
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
}