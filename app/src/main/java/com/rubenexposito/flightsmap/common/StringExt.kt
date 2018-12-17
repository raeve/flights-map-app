package com.rubenexposito.flightsmap.common

import java.text.SimpleDateFormat
import java.util.*

fun String.toHoursMinutes() : String {
    val date = SimpleDateFormat(if(contains("M")) "'PT'HH'H'mm'M'" else "'PT'HH'H'", Locale.getDefault()).parse(this)
    return SimpleDateFormat("HH'h 'mm'min'", Locale.getDefault()).format(date)
}

fun String.toHours() : String {
    val date = SimpleDateFormat("YYYY-MM-dd'T'HH:mm", Locale.getDefault()).parse(this)
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
}