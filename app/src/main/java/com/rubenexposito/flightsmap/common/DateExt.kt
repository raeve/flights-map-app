package com.rubenexposito.flightsmap.common

import java.text.SimpleDateFormat
import java.util.*


fun Date.toDateTime() = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault()).format(this)
