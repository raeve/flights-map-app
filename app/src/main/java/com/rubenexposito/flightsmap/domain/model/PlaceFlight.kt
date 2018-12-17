package com.rubenexposito.flightsmap.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PlaceFlight(val airportCode: String, val date: String, val city: String = "") : Parcelable