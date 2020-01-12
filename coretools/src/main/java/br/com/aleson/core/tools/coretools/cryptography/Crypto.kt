package br.com.aleson.core.tools.coretools.cryptography

import br.com.aleson.core.tools.coretools.cryptography.aes.AES
import br.com.aleson.core.tools.coretools.cryptography.rsa.RSA

interface Crypto {

    fun RSA(publicKey: String): RSA

    fun AES(): AES

}