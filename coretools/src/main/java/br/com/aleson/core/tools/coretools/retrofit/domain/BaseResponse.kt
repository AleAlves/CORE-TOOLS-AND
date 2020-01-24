package br.com.aleson.core.tools.coretools.retrofit.domain

import com.google.gson.annotations.SerializedName

class BaseResponse (
    @SerializedName("code")
    var code: Int,
    @SerializedName("status")
    var status: String
)