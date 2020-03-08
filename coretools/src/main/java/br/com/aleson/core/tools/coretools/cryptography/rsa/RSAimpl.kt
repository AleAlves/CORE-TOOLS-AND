package br.com.aleson.core.tools.coretools.cryptography.rsa

import android.util.Base64
import com.google.gson.Gson
import java.security.KeyFactory
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

const val RSA_KDF = "RSA"
const val RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding"
val PUBLIC_KEY_CLEN_REGEX = "(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)".toRegex()

class RSAimpl(private val publicKey: String) : RSA {


    private fun clearPublicKey(publicKey: String): String {
        return publicKey.replace(PUBLIC_KEY_CLEN_REGEX, "")
    }

    private fun clearDataBreakLines(data: String): String {
        return data.replace("\n", "").replace("\n", "")
    }

    private fun encodeBase64(encrypted: ByteArray?): String {
        return Base64.encodeToString(encrypted, 0).replace("\n", "")
    }

    override fun publicKey(): String {
        return publicKey
    }


    override fun encrypt(data: String): String {
        val plainData = clearDataBreakLines(data)
        val publicKey = clearPublicKey(publicKey)
        val keyBytes = Base64.decode(publicKey, Base64.DEFAULT)
        val spec = X509EncodedKeySpec(keyBytes)
        val kf = KeyFactory.getInstance(RSA_KDF)
        val pk = kf.generatePublic(spec)
        var cipherText: ByteArray?
        val cipher = Cipher.getInstance(RSA_PADDING_SCHEME)
        cipher.init(Cipher.ENCRYPT_MODE, pk)
        cipherText = cipher.doFinal(plainData.toByteArray())
        return encodeBase64(cipherText)
    }

    override fun encrypt(data: Any): String {
        val plainData = clearDataBreakLines(Gson().toJson(data))
        val publicKey = clearPublicKey(publicKey)
        val keyBytes = Base64.decode(publicKey, Base64.DEFAULT)
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