package com.rubenexposito.flightsmap.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.rubenexposito.flightsmap.common.fromJson
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.dto.OperationsSchedulesDto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SchedulesInteractorImplTest : BaseInteractorTest() {

    @Mock
    lateinit var lufthansaRepository: LufthansaRepository

    private val interactor by lazy {
        SchedulesInteractorImpl(lufthansaRepository)
    }

    private lateinit var operationsSchedulesDto: OperationsSchedulesDto

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        operationsSchedulesDto = getResource("operations_schedules.json").fromJson(OperationsSchedulesDto::class.java)
    }

    @Test
    fun `should retrieve list of schedules on successful response`() {
        whenever(lufthansaRepository.operationsSchedules(any(), any(), any())).thenReturn(Single.just(operationsSchedulesDto))

        val result = interactor.getSchedules("","","")
            .observeOn(Schedulers.trampoline())
            .subscribeOn(Schedulers.trampoline())
            .test()

        result.assertNoErrors()
        val value = result.values().first()
        assertNotNull(value)
        assert(value.size == operationsSchedulesDto.scheduleResource.scheduleList.size)
    }
}