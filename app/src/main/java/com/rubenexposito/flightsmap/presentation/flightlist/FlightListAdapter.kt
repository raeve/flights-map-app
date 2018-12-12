package com.rubenexposito.flightsmap.presentation.flightlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import kotlinx.android.synthetic.main.item_airport.view.*
import kotlinx.android.synthetic.main.item_schedule.view.*

class FlightListAdapter(private val listener: ItemListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var airportList: MutableList<Airport> = ArrayList()
    var scheduleList: MutableList<Schedule> = ArrayList()

    var from = true

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = if (viewType == VIEW_TYPE_AIRPORT) AirportViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_airport, parent, false))
    else ScheduleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false))

    override fun getItemViewType(position: Int): Int = if (scheduleList.isEmpty()) VIEW_TYPE_AIRPORT else VIEW_TYPE_SCHEDULE
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getItemCount(): Int = if (scheduleList.isEmpty()) airportList.size else scheduleList.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is AirportViewHolder) {
            val airport = airportList[position]
            holder.bind(airport)
            holder.itemView.setOnClickListener { listener.onAirportSelected(airport, from) }
        }

        if (holder is ScheduleViewHolder) {
            val schedule = scheduleList[position]
            holder.bind(schedule)
            holder.itemView.setOnClickListener { listener.onScheduleSelected(schedule) }
        }
    }

    fun addAirports(airportList: List<Airport>) {
        this.airportList.addAll(airportList)
    }

    companion object {
        const val VIEW_TYPE_AIRPORT = 123
        const val VIEW_TYPE_SCHEDULE = 321
    }
}

interface ItemListener {
    fun onAirportSelected(airport: Airport, from: Boolean)
    fun onScheduleSelected(schedule: Schedule)
}

class AirportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Airport) = with(itemView) {
        tvAirportCode.text = item.airportCode
        val city = "${item.city}(${item.countryCode})"
        tvAirportCity.text = city
    }
}

class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Schedule) = with(itemView) {
        tvFlightDuration.text = item.duration
        val departure = "${item.flights[0].departure.airportCode}-${item.flights[0].departure.date}"
        tvDeparture.text = departure
        val arrival = "${item.flights[0].arrival.airportCode}-${item.flights[0].arrival.date}"
        tvArrival.text = arrival
    }
}
