package br.com.aleson.core.tools.app

import com.google.gson.annotations.SerializedName

data class SessionToken(
    @SerializedName("sessionToken")
    val sessionToken: String
)