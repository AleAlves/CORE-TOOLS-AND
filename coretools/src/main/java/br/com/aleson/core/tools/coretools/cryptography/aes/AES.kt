package br.com.aleson.core.tools.coretools.cryptography.aes

interface AES{

    fun key() : String

    fun salt()  : String

    fun iv() : String

    fun encrypt(data: Any) : String

    fun encrypt(data: String) : String

    fun decrypt(data: Any?) : String ?

    fun decrypt(data: String?) : String ?

}