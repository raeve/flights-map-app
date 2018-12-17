package com.rubenexposito.flightsmap.presentation.flightlist

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.rubenexposito.flightsmap.Navigator
import com.rubenexposito.flightsmap.domain.AirportsInteractor
import com.rubenexposito.flightsmap.domain.OAuthInteractor
import com.rubenexposito.flightsmap.domain.SchedulesInteractor
import com.rubenexposito.flightsmap.domain.model.Airport
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FlightListPresenterTest {

    @Mock
    lateinit var view: FlightListContract.View

    @Mock
    lateinit var oAuthInteractor: OAuthInteractor

    @Mock
    lateinit var airportsInteractor: AirportsInteractor

    @Mock
    lateinit var schedulesInteractor: SchedulesInteractor

    @Mock
    lateinit var navigator: Navigator

    private val presenter by lazy {
        FlightListPresenter(
            view,
            oAuthInteractor,
            airportsInteractor,
            schedulesInteractor,
            navigator,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        presenter.airportCodeFrom = "ZRH"
        presenter.airportCodeTo = "ZRH"
    }

    @Test
    fun `should call on prepare when request token is successful on create`() {
        givenOAuthSuccess()

        presenter.onCreate()
        verify(view).onPrepared()
    }

    @Test
    fun `should show error when request token error on create`() {
        givenOAuthError()

        presenter.onCreate()
        verify(view).showError(any())
    }

    @Test
    fun `should dispose subscription on pause`() {
        presenter.onPause()
        assert(presenter.subscription.isDisposed)
    }

    @Test
    fun `should show airports when get airports is successful`(){
        val list = listOf(Airport("", "", "", null, null))
        givenAirportsSuccess(list)

        presenter.requestAirports(true)
        verify(view).showAirports(list,true)
    }

    @Test
    fun `should show empty when get airports is error`(){
        givenAirportsError()

        presenter.requestAirports(true)
        verify(view).showEmpty()
    }

    @Test
    fun `should add airports when get more airports is successful`(){
        val list = listOf(Airport("", "", "", null, null))
        givenAirportsSuccess(list)

        presenter.requestMoreAirports()
        verify(view).addAirports(list)
    }

    @Test
    fun `should show error when get more airports is error`(){
        givenAirportsError()

        presenter.requestMoreAirports()
        verify(view).showError(any())
    }

    @Test
    fun `should show schedules when get schedules is successful on airport selected`(){
        val list = listOf(Schedule("", emptyList()))
        givenSchedulesSuccess(list)

        presenter.onAirportSelected(Airport("ZRH", "","", null, null),true)
        verify(view).showSchedules(list)
    }

    @Test
    fun `should show empty when get schedules is error on airport selected`(){
        givenSchedulesError()

        presenter.onAirportSelected(Airport("ZRH", "","", null, null),true)
        verify(view).showEmpty()
    }

    @Test
    fun `should navigate to map when on schedule selected`() {
        presenter.onScheduleSelected(Schedule("", emptyList()))
        verify(navigator).showMap(any())
    }

    private fun givenOAuthError() {
        whenever(oAuthInteractor.getAuthToken()).thenReturn(Single.error(Throwable()))
    }

    private fun givenOAuthSuccess() {
        whenever(oAuthInteractor.getAuthToken()).thenReturn(Single.just(true))
    }

    private fun givenAirportsError() {
        whenever(airportsInteractor.getAirports(any(), any())).thenReturn(Single.error(Throwable()))
    }

    private fun givenAirportsSuccess(list: List<Airport>) {
        whenever(airportsInteractor.getAirports(any(), any())).thenReturn(Single.just(list))
    }

    private fun givenSchedulesError() {
        whenever(schedulesInteractor.getSchedules(any(), any(), any())).thenReturn(Single.error(Throwable()))
    }

    private fun givenSchedulesSuccess(list: List<Schedule>) {
        whenever(schedulesInteractor.getSchedules(any(), any(), any())).thenReturn(Single.just(list))
    }
}