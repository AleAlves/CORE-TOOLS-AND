package br.com.aleson.core.tools.coretools.cryptography.rsa

import android.util.Base64
import br.com.aleson.core.tools.coretools.cryptography.PUBLIC_KEY_CLEN_REGEX
import br.com.aleson.core.tools.coretools.cryptography.RSA_KDF
import br.com.aleson.core.tools.coretools.cryptography.RSA_PADDING_SCHEME
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher


class RSAimpl(private var publicKey: String) : RSA {

    private fun clearPublicKey(publicKey: String): String {
        return publicKey.replace(PUBLIC_KEY_CLEN_REGEX, "")
    }

    private fun clearDataBreakLines(data: String): String {
        return data.replace("\n", "").replace("\n", "")
    }

    private fun encodeBase64(encrypted: ByteArray?): String {
        return Base64.encodeToString(encrypted, 0).replace("\n", "")
    }

    override fun encrypt(data: String): String {
        val plainData = clearDataBreakLines(data)
        val publicKey = clearPublicKey(publicKey)
        val keyBytes = Base64.decode(publicKey, 0)
        val spec = X509EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance(RSA_KDF)
        val pk = kf.generatePublic(spec)
        var cipherText: ByteArray?
        val cipher = Cipher.getInstance(RSA_PADDING_SCHEME)
        cipher.init(Cipher.ENCRYPT_MODE, pk)
        cipherText = cipher.doFinal(plainData.toByteArray())
        return encodeBase64(cipherText)
    }

}