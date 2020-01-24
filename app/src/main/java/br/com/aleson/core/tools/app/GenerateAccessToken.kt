package br.com.aleson.core.tools.app

import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPRequest
import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GenerateAccessToken {

    @POST("api/v1/access-token")
    fun token(@Body body: HTTPRequest<String>): Call<HTTPResponse<AccessToken>>

}