package br.com.aleson.core.tools.app

import br.com.aleson.core.tools.coretools.cryptography.model.PublicKey
import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPResponse
import retrofit2.Call
import retrofit2.http.GET

interface PublicKeyRequest {

    @GET("api/v1/publicKey")
    fun getPublicKey(): Call<HTTPResponse<PublicKey>>

}