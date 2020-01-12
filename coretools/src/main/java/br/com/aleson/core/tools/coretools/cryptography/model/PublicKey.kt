package br.com.aleson.core.tools.coretools.cryptography.model

import br.com.aleson.core.tools.coretools.Status
import com.google.gson.annotations.SerializedName

data class PublicKey(
    @SerializedName("publicKey")
    val publicKey: String,

    @SerializedName("status")
    var status: Status
)
