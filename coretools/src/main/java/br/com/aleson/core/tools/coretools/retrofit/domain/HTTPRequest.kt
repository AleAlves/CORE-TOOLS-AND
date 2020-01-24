package br.com.aleson.core.tools.coretools.retrofit.domain

import com.google.gson.annotations.SerializedName

open class HTTPRequest<E>(
    @SerializedName("data")
    var data: E
)
