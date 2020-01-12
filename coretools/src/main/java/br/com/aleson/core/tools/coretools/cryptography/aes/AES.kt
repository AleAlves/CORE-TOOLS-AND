package br.com.aleson.core.tools.coretools.cryptography.aes

interface AES{

    fun encrypt(data: String) : String

    fun decrypt(data: String?) : String ?

}