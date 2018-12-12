package com.rubenexposito.flightsmap.data.network

import com.rubenexposito.flightsmap.data.dto.OAuthTokenDto
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
    fun referencesAirports(@Header("Authorization") authorization: String,
                           @Header("Accept") accept: String = "application/json",
                           @Query("limit") limit: Int,
                           @Query("offset") offset: Int,
                           @Query("LHoperated") lhOperated: Boolean = true
    ): Single<ReferencesAirportDto>
}