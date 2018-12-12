package com.rubenexposito.flightsmap.data

import com.rubenexposito.flightsmap.data.dto.OAuthTokenDto
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import com.rubenexposito.flightsmap.data.network.LufthansaApi
import io.reactivex.Single

interface LufthansaRepository {
    fun oauthToken() : Single<OAuthTokenDto>
    fun referencesAirport(authorization: String, limit: Int, offset: Int) : Single<ReferencesAirportDto>
}

class LufthansaRepositoryImpl(private val lufthansaApi: LufthansaApi) : LufthansaRepository {
    override fun oauthToken(): Single<OAuthTokenDto> = lufthansaApi.oauthToken()
    override fun referencesAirport(authorization: String, limit: Int, offset: Int): Single<ReferencesAirportDto> = lufthansaApi.referencesAirports(authorization, limit = limit, offset = offset)
}