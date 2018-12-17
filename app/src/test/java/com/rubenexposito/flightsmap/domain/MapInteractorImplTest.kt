package com.rubenexposito.flightsmap.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.rubenexposito.flightsmap.common.fromJson
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.domain.model.Flight
import com.rubenexposito.flightsmap.domain.model.PlaceFlight
import com.rubenexposito.flightsmap.domain.model.Schedule
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MapInteractorImplTest : BaseInteractorTest(){

    @Mock
    lateinit var lufthansaRepository: LufthansaRepository

    private val interactor by lazy {
        MapInteractorImpl(lufthansaRepository)
    }

    private lateinit var referencesAirportDto: ReferencesAirportDto

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        referencesAirportDto = getResource("references_airports_zrh.json").fromJson(ReferencesAirportDto::class.java)
    }

    @Test
    fun `should load airports when response is successful`(){
        whenever(lufthansaRepository.referencesAirport(any())).thenReturn(Single.just(referencesAirportDto))

        val result = interactor.getAirports(Schedule("", listOf(Flight(PlaceFlight("ZRH",""), PlaceFlight("ZRH",""),""))))
            .observeOn(Schedulers.trampoline())
            .subscribeOn(Schedulers.trampoline())
            .test()

        result.assertNoErrors()
        val value = result.values().first()
        assertNotNull(value)
        assert(value.size == referencesAirportDto.airportResource.airportsDto.listAirportDto.size+1)
    }
}