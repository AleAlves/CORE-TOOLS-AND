package br.com.aleson.core.tools.coretools.retrofit.domain

import com.google.gson.annotations.SerializedName

open class HTTPResponse<E>(
    @SerializedName("data")
    var data: E,
    @SerializedName("response")
    var response: BaseResponse
)

