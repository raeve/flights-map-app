package com.rubenexposito.flightsmap.data.network

import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LufthansaApi {

    @GET("references/airports")
    fun referencesAirports(@Query("limit") limit: Int, @Query("offset") offset: Int, @Query("LHOperated") lhOperated: Boolean = true): Single<ReferencesAirportDto>
}