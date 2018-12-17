package com.rubenexposito.flightsmap.presentation.flightlist

import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.common.hide
import com.rubenexposito.flightsmap.common.show
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_flight_list.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_progress.*
import javax.inject.Inject

class FlightListActivity : AppCompatActivity(), FlightListContract.View {

    @Inject
    lateinit var presenter: FlightListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)
        AndroidInjection.inject(this)
        presenter.onCreate()
        initView()
    }

    override fun onPrepared() {
        tvFrom.callOnClick()
    }

    override fun onPause() {
        presenter.onPause()
        super.onPause()
    }

    override fun clearAirports() {
        with(rvItems.adapter as FlightListAdapter) {
            this.airportList.clear()
            this.scheduleList.clear()
            notifyDataSetChanged()
        }
        rvItems.show()
    }

    override fun showAirports(airportList: List<Airport>, from: Boolean) {
        with(rvItems.adapter as FlightListAdapter) {
            this.from = from
            this.scheduleList.clear()
            this.airportList = airportList.toMutableList()
            notifyDataSetChanged()
        }
        rvItems.show()
    }

    override fun addAirports(airportList: List<Airport>) = with(rvItems.adapter as FlightListAdapter) {
        val positionStart = itemCount
        addAirports(airportList)
        notifyItemRangeInserted(positionStart, airportList.size)
    }

    override fun showSchedules(scheduleList: List<Schedule>) {
        with(rvItems.adapter as FlightListAdapter) {
            this.airportList.clear()
            this.scheduleList = scheduleList.toMutableList()
            notifyDataSetChanged()
        }
        rvItems.show()
    }

    override fun showError(@StringRes resId: Int) = Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()

    override fun showLoading() {
        rvItems.hide()
        emptyView.hide()
        progressView.show()
    }

    override fun hideLoading() {
        progressView.hide()
    }

    override fun showEmpty() {
        emptyView.show()
    }


    override fun updateAirportTo(text: String) = with(tvTo) {
        this.text = text
        setTextColor(getColor(R.color.black))
        typeface = Typeface.DEFAULT
    }

    override fun updateAirportFrom(text: String) {
        with(tvFrom) {
            this.text = text
            setTextColor(getColor(R.color.black))
            typeface = Typeface.DEFAULT
        }
    }

    override fun selectAirportFrom() {
        tvFrom.setTextColor(getColor(R.color.colorPrimaryDark))
        tvFrom.typeface = Typeface.DEFAULT_BOLD
        presenter.requestAirports(true)
    }

    override fun selectAirportTo() {
        tvTo.setTextColor(getColor(R.color.colorPrimaryDark))
        tvTo.typeface = Typeface.DEFAULT_BOLD
        presenter.requestAirports(false)
    }

    private fun initView() {
        tvFrom.setOnClickListener {
            selectAirportFrom()
        }
        tvTo.setOnClickListener {
            selectAirportTo()
        }

        with(rvItems) {
            this.layoutManager = LinearLayoutManager(this@FlightListActivity)
            adapter = FlightListAdapter(presenter)
            setHasFixedSize(true)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val adapter = recyclerView.adapter as FlightListAdapter
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.itemCount - 1 && adapter.airportList.isNotEmpty()) {
                        presenter.requestMoreAirports()
                    }

                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }
    }
}