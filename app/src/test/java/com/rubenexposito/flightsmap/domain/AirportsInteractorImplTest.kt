package com.rubenexposito.flightsmap.domain

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.whenever
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.domain.mapper.AirportMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AirportsInteractorImplTest : BaseInteractorTest() {

    @Mock
    lateinit var lufthansaRepository: LufthansaRepository

    @Mock
    lateinit var airportMapper: AirportMapper

    private lateinit var referencesAirportDto: ReferencesAirportDto

    private val interactor by lazy {
        AirportsInteractorImpl(lufthansaRepository, airportMapper)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        referencesAirportDto =
                Gson().fromJson(getResource("references_airports.json").readText(), ReferencesAirportDto::class.java)
    }

    @Test
    fun `should retrieve list of airports given successful response`() {
        whenever(lufthansaRepository.referencesAirport(20, 0)).thenReturn(Single.just(referencesAirportDto))

        val result = interactor.getAirports(20, 0)
            .observeOn(Schedulers.trampoline())
            .subscribeOn(Schedulers.trampoline())
            .test()

        result.assertNoErrors()
        val airportList = result.values().first()
        assertNotNull(airportList)
        assert(airportList.size == referencesAirportDto.airportResource.airportsDto.listAirportDto.size)
    }
}