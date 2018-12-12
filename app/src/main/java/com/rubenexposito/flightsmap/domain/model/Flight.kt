package com.rubenexposito.flightsmap.domain.model

data class Flight(val departure: PlaceFlight, val arrival: PlaceFlight, val airplane: String)