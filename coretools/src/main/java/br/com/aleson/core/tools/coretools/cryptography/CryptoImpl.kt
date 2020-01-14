package br.com.aleson.core.tools.coretools.cryptography

import br.com.aleson.core.tools.coretools.cryptography.aes.AES
import br.com.aleson.core.tools.coretools.cryptography.aes.AESImpl
import br.com.aleson.core.tools.coretools.cryptography.rsa.RSA
import br.com.aleson.core.tools.coretools.cryptography.rsa.RSAimpl

const val AES_KDF = "PBKDF2WithHmacSHA1"
const val RSA_KDF = "RSA"
const val AES_ALGORITHM = "AES"
const val RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding"
const val AES_PADDING_SCHEME = "AES/CBC/PKCS5Padding"
const val STANDAR_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
val PUBLIC_KEY_CLEN_REGEX = "(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)".toRegex()

class CryptoImpl : Crypto {

    private var rsa: RSA? = null
    private var aes: AES? = null

    override fun RSA(publicKey: String): RSA {
        if (rsa == null) {
            rsa = RSAimpl(publicKey)
        }
        return rsa!!
    }

    override fun AES(): AES {
        if (aes == null) {
            aes = AESImpl()
        }
        return aes!!
    }

}