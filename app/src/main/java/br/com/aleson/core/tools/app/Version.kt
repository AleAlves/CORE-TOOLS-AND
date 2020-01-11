package br.com.aleson.core.tools.app

import com.google.gson.annotations.SerializedName

data class Version(
    @SerializedName("version")
    var version: String,
    @SerializedName("status")
    var status: Status
)

data class Status(
    @SerializedName("code")
    var code: Int,
    @SerializedName("status")
    var status: String
)