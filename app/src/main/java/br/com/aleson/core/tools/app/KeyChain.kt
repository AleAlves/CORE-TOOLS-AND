package br.com.aleson.core.tools.app

import com.google.gson.annotations.SerializedName

data class KeyChain(
    @SerializedName("uid")
    val uid: String,
    @SerializedName("AESKey")
    val AESKey: String,
    @SerializedName("AESSalt")
    val AESSalt: String,
    @SerializedName("AESIV")
    val AESIV: String
)