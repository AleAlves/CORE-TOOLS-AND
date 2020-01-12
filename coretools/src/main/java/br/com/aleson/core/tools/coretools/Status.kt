package br.com.aleson.core.tools.coretools

import com.google.gson.annotations.SerializedName


data class Status(
    @SerializedName("code")
    var code: Int,
    @SerializedName("status")
    var status: String
)