package com.rubenexposito.flightsmap.data.network

import com.rubenexposito.flightsmap.data.dto.OAuthTokenDto
import com.rubenexposito.flightsmap.data.dto.OperationsSchedulesDto
import com.rubenexposito.flightsmap.data.dto.ReferencesAirportDto
import io.reactivex.Single
import retrofit2.http.*

interface LufthansaApi {

    @FormUrlEncoded
    @POST("oauth/token")
    fun oauthToken(
        @Field("client_id") clientId: String = NetworkConfig.API_CLIENT_ID,
        @Field("client_secret") clientSecret: String = NetworkConfig.API_CLIENT_SECRET,
        @Field("grant_type") grantType: String = "client_credentials"
    ): Single<OAuthTokenDto>

    @GET("references/airports")
    fun referencesAirports(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("LHoperated") lhOperated: Boolean = false
    ): Single<ReferencesAirportDto>

    @GET("references/airports/{airportCode}")
    fun referencesAirports(
        @Path("airportCode") airportCode: String,
        @Query("LHoperated") lhOperated: Boolean = false
    ): Single<ReferencesAirportDto>

    @GET("operations/schedules/{fromCode}/{toCode}/{date}")
    fun operationsSchedules(
        @Path("fromCode") fromCode: String,
        @Path("toCode") toCode: String,
        @Path("date") date: String,
        @Query("directFlights") directFlights: Boolean = false
    ): Single<OperationsSchedulesDto>
}