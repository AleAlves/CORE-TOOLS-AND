package br.com.aleson.core.tools.coretools.cryptography

import br.com.aleson.core.tools.coretools.cryptography.aes.AES
import br.com.aleson.core.tools.coretools.cryptography.aes.AESImpl
import br.com.aleson.core.tools.coretools.cryptography.rsa.RSA
import br.com.aleson.core.tools.coretools.cryptography.rsa.RSAimpl

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