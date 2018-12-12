package com.rubenexposito.flightsmap.domain

import com.rubenexposito.flightsmap.data.LufthansaRepository
import com.rubenexposito.flightsmap.data.SharedRepository
import io.reactivex.Single

interface OAuthInteractor {
    fun getAuthToken(): Single<Boolean>
}

class OAuthInteractorImpl(
    private val lufthansaRepository: LufthansaRepository,
    private val sharedRepository: SharedRepository
) : OAuthInteractor {

    override fun getAuthToken() = lufthansaRepository.oauthToken()
        .map {
            sharedRepository.saveToken(it.accessToken, it.tokenType, it.expiresIn)
        }
}