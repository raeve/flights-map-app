package com.rubenexposito.flightsmap.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Flight(val departure: PlaceFlight, val arrival: PlaceFlight, val airplane: String) : Parcelable