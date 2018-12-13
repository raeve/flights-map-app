package com.rubenexposito.flightsmap.presentation.flightmap

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.rubenexposito.flightsmap.R
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import dagger.android.AndroidInjection
import javax.inject.Inject

class FlightMapActivity : AppCompatActivity(), FlightMapContract.View, OnMapReadyCallback {

    @Inject
    lateinit var presenter: FlightMapContract.Presenter

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_maps)
        AndroidInjection.inject(this)
        initMap()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        initExtra()
    }

    override fun drawLines(airports: List<Airport>) {
        var index = 1

        for(airport in airports) {
            val position = LatLng(airport.latitude, airport.longitude)

            if(index == 1) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            }

            mMap.addMarker(MarkerOptions().position(position).title(airport.city))

            if(index == airports.size) {
                break
            }

            val next = airports[index]
            val nextPosition = LatLng(next.latitude, next.longitude)

            mMap.addPolyline(PolylineOptions().add(position, nextPosition).width(4f).color(getColor(R.color.colorPrimary)))
            index++
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showError(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
    }

    private fun initMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun updateMap() {

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun initExtra(){
        intent?.let{
            presenter.addSchedule(intent.getParcelableExtra(KEY_SCHEDULE))
        }
    }

    companion object {
        private const val KEY_SCHEDULE = "key:schedule"

        fun getIntent(context: Context, schedule: Schedule) = Intent(context, FlightMapActivity::class.java).run{
            putExtra(KEY_SCHEDULE, schedule)
        }
    }
}
