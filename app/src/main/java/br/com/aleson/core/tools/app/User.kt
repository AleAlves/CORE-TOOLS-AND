package br.com.aleson.core.tools.app

import com.google.gson.annotations.SerializedName

class User(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("picture")
    val picture: String,
    @SerializedName("firebaseID")
    val firebaseID: String
)