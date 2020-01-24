package br.com.aleson.core.tools.app

import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPRequest
import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginRequest {

    @POST("api/v1/login")
    fun login(@Header("access-token")  accessToken : String, @Body body: HTTPRequest<String>): Call<HTTPResponse<SessionToken>>

}