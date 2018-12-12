package com.rubenexposito.flightsmap.data.dto

import com.google.gson.annotations.SerializedName

data class OperationsSchedulesDto(@SerializedName("ScheduleResource") val scheduleResource: ScheduleResourceDto)

data class ScheduleResourceDto(@SerializedName("Schedule") val scheduleList: List<ScheduleDto>)

data class ScheduleDto(@SerializedName("TotalJourney") val totalJourney: TotalJourneyDto,
                       @SerializedName("Flight") val flightList: List<FlightDto>)

data class TotalJourneyDto(@SerializedName("Duration") val duration: String)

data class FlightDto(@SerializedName("Departure") val departure: DepartureArrivalDto,
                     @SerializedName("Arrival") val arrival: DepartureArrivalDto,
                     @SerializedName("MarketingCarrier") val marketingCarrier: MarketingCarrierDto,
                     @SerializedName("Equipment") val equipment: EquipmentDto,
                     @SerializedName("Details") val details: DetailsDto)

data class DepartureArrivalDto(@SerializedName("AirportCode") val airportCode: String,
                               @SerializedName("ScheduledTimeLocal") val scheduledTimeLocal: ScheduledTimeLocalDto,
                               @SerializedName("Terminal") val terminalDto: TerminalDto)

data class ScheduledTimeLocalDto(@SerializedName("DateTime") val dateTime: String)

data class TerminalDto(@SerializedName("Name") val name: Int)

data class MarketingCarrierDto(@SerializedName("AirlineID") val airlineId: String,
                               @SerializedName("FlightNumber") val flightNumber: Int)

data class EquipmentDto(@SerializedName("AircraftCode") val aircraftCode: String,
                        @SerializedName("OnBoardEquipment") val onBoardEquipment: OnBoardEquipmentDto)

data class OnBoardEquipmentDto(@SerializedName("InflightEntertainment") val inflightEntertainment: Boolean,
                               @SerializedName("Compartment") val compartmentList: List<CompartmentDto>)

data class CompartmentDto(@SerializedName("ClassCode") val classCode: String,
                          @SerializedName("ClassDesc") val classDesc: String,
                          @SerializedName("FlyNet") val flyNet: Boolean,
                          @SerializedName("SeatPower") val seatPower: Boolean,
                          @SerializedName("Usb") val usb: Boolean,
                          @SerializedName("LiveTv") val liveTv: Boolean)

data class DetailsDto(@SerializedName("Stops") val stops: StopsDto,
                      @SerializedName("DaysOfOperation") val daysOfOperation: Int,
                      @SerializedName("DatePeriod") val datePeriod: DatePeriodDto)

data class StopsDto(@SerializedName("StopQuantity") val stopQuantity: String)

data class DatePeriodDto(@SerializedName("Effective") val effective: String,
                         @SerializedName("Expiration") val expiration: String)

