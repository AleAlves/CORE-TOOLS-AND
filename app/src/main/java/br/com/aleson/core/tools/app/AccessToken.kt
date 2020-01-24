package br.com.aleson.core.tools.app

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("accessToken")
    val accessToken: String
)