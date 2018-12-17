package com.rubenexposito.flightsmap.domain

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.rubenexposito.flightsmap.common.fromJson
import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.SharedRepository
import com.rubenexposito.flightsmap.data.dto.OAuthTokenDto
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class OAuthInteractorImplTest : BaseInteractorTest() {

    @Mock
    lateinit var lufthansaRepository: LufthansaRepository

    @Mock
    lateinit var sharedRepository: SharedRepository

    private lateinit var oAuthTokenDto: OAuthTokenDto

    private val interactor by lazy {
        OAuthInteractorImpl(lufthansaRepository, sharedRepository)
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        oAuthTokenDto = getResource("oauth_token.json").fromJson(OAuthTokenDto::class.java)
    }

    @Test
    fun `should retrieve token given successful response`() {
        whenever(lufthansaRepository.oauthToken()).thenReturn(Single.just(oAuthTokenDto))
        whenever(sharedRepository.saveToken(any(), any(), any())).thenReturn(true)

        val result = interactor.getAuthToken()
            .observeOn(Schedulers.trampoline())
            .subscribeOn(Schedulers.trampoline())
            .test()

        result.assertNoErrors()

        val value = result.values().first()
        assertNotNull(value)
        assert(value == oAuthTokenDto.accessToken.isNotEmpty())
    }
}