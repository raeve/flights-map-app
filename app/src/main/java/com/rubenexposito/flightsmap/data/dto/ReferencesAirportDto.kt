package com.rubenexposito.flightsmap.data.dto

import com.google.gson.annotations.SerializedName

data class ReferencesAirportDto(@SerializedName("AirportResource") val airportResource: AirportResourceDto)

data class AirportResourceDto(@SerializedName("Airports") val airportsDto: AirportsDto)

data class AirportsDto(@SerializedName("Airport") val listAirportDto: List<AirportDto>)

data class AirportDto(
    @SerializedName("AirportCode") val airportCode: String,
    @SerializedName("Position") val positionDto: PositionDto,
    @SerializedName("CityCode") val cityCode: String,
    @SerializedName("CountryCode") val countryCode: String,
    @SerializedName("LocationType") val locationType: String,
    @SerializedName("Names") val namesDto: NamesDto,
    @SerializedName("UtcOffset") val utcOffset: Int,
    @SerializedName("TimeZoneId") val timeZoneId: String
)

data class PositionDto(@SerializedName("Coordinate") val coordinateDto: CoordinateDto)

data class CoordinateDto(@SerializedName("Latitude") val latitude: Double, @SerializedName("Longitude") val longitude: Double)

data class NamesDto(@SerializedName("Name") val listNameDto: List<NameDto>)

data class NameDto(@SerializedName("@LanguageCode") val languageCode: String, @SerializedName("$") val alias: String)
