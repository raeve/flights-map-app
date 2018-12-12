package com.rubenexposito.flightsmap.presentation.flightlist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.domain.model.Airport
import kotlinx.android.synthetic.main.item_airport.view.*

class FlightListAdapter(private val listener: ItemListener) : RecyclerView.Adapter<AirportViewHolder>() {
    var airportList: MutableList<Airport> = ArrayList()
    var scheduleList: MutableList<>

    var from = true

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportViewHolder = AirportViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_airport, parent, false))
    override fun getItemViewType(position: Int): Int = VIEW_TYPE_AIRPORT
    override fun getItemId(position: Int): Long = airportList[position].airportCode.hashCode().toLong()
    override fun getItemCount(): Int = airportList.size
    override fun onBindViewHolder(holder: AirportViewHolder, position: Int) {
        val item = airportList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener.onAirportSelected(item, from) }
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
}

class AirportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: Airport) = with(itemView) {
        tvAirportCode.text = item.airportCode
        val city = "${item.city}(${item.countryCode})"
        tvAirportCity.text = city
    }
}
