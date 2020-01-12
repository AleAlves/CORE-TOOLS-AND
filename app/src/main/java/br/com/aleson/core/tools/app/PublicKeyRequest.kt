package br.com.aleson.core.tools.app

import br.com.aleson.core.tools.coretools.cryptography.model.PublicKey
import retrofit2.Call
import retrofit2.http.GET

interface PublicKeyRequest {

    @GET("api/v1/init")
    fun getPublicKey(): Call<PublicKey>

}