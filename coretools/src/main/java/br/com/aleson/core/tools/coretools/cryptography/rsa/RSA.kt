package br.com.aleson.core.tools.coretools.cryptography.rsa


interface RSA {

    fun publicKey() : String

    fun encrypt(data: String) : String

    fun encrypt(data: Any) : String

}