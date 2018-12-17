package com.rubenexposito.flightsmap.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Schedule(val duration: String, val flights: List<Flight>) : Parcelable