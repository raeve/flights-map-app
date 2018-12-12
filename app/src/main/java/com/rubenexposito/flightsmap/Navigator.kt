package com.rubenexposito.flightsmap

import android.app.Activity
import com.rubenexposito.flightsmap.domain.model.Schedule

interface Navigator {
    fun showMap(schedule: Schedule)
}

class NavigatorImpl(private val activity: Activity) : Navigator {
    override fun showMap(schedule: Schedule) {

    }
}
