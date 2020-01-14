package br.com.aleson.core.tools.coretools.cryptography.model

import com.google.gson.annotations.SerializedName

data class PublicKey(
    @SerializedName("publicKey")
    val publicKey: String
)
