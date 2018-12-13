package com.rubenexposito.flightsmap

import android.app.Activity
import com.rubenexposito.flightsmap.domain.model.Schedule
import com.rubenexposito.flightsmap.presentation.flightmap.FlightMapActivity

interface Navigator {
    fun showMap(schedule: Schedule)
}

class NavigatorImpl(private val activity: Activity) : Navigator {
    override fun showMap(schedule: Schedule) = activity.startActivity(FlightMapActivity.getIntent(activity, schedule))
}
