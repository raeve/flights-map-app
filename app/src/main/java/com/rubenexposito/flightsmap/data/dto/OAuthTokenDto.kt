package com.rubenexposito.flightsmap.data.dto

import com.google.gson.annotations.SerializedName

data class OAuthTokenDto(@SerializedName("access_token") val accessToken: String, @SerializedName("token_type") val tokenType: String, @SerializedName("expires_in") val expiresIn: Long)