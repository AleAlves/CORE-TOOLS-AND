package br.com.aleson.core.tools.app

import retrofit2.Call
import retrofit2.http.GET

interface GetRequest{

    @GET("api/v1/version")
    fun getMethod() : Call<Version>

}