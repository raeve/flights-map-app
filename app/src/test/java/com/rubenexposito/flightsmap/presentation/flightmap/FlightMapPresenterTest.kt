package com.rubenexposito.flightsmap.presentation.flightmap

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rubenexposito.flightsmap.domain.MapInteractor
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlightMapPresenterTest {

    @Mock
    lateinit var view: FlightMapContract.View

    @Mock
    lateinit var interactor: MapInteractor

    private val presenter by lazy {
        FlightMapPresenter(view, interactor, Schedulers.trampoline(), Schedulers.trampoline())
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `should dispose subscription on pause`() {
        presenter.onPause()
        presenter.subscription?.isDisposed?.let { assert(it) }
    }

    @Test
    fun `should draw lines when given success on add schedule`() {
        givenMapSucess()

        presenter.addSchedule(Schedule("", emptyList()))
        verify(view).drawLines(any())
    }

    @Test
    fun `should show error when given error on add schedule`() {
        givenMapError()

        presenter.addSchedule(Schedule("", emptyList()))
        verify(view).showError(any())
    }


    private fun givenMapError() {
        whenever(interactor.getAirports(any())).thenReturn(Single.error(Throwable()))
    }

    private fun givenMapSucess() {
        whenever(interactor.getAirports(any())).thenReturn(Single.just(emptyList()))
    }
}