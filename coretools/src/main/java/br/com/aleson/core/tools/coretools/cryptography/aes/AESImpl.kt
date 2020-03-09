package br.com.aleson.core.tools.coretools.cryptography.aes

import android.util.Base64
import android.util.Base64.DEFAULT
import com.google.gson.Gson
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

const val AES_KDF = "PBKDF2WithHmacSHA1"
const val AES_ALGORITHM = "AES"
const val AES_PADDING_SCHEME = "AES/CBC/PKCS5Padding"
const val STANDAR_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"

class AESImpl : AES {

    private var cipher: Cipher? = null
    private var secretKeySpec: SecretKeySpec? = null
    private val iterationCount = 2048
    private val keyStrength = 256
    private val keyLength = 16
    private val saltLength = 8
    private var ivParameterSpec: IvParameterSpec? = null
    private var key: String
    private var salt: String
    private var iv: ByteArray?

    init {
        key = generateKey(keyLength)
        salt = generateSalt(saltLength)
        iv = generateIv(keyLength).toByteArray(Charset.defaultCharset())
        val factory = SecretKeyFactory.getInstance(AES_KDF)
        val spec = PBEKeySpec(key.toCharArray(), salt.toByteArray(), iterationCount, keyStrength)
        val secretKey = factory.generateSecret(spec)
        secretKeySpec = SecretKeySpec(secretKey.encoded, AES_ALGORITHM)
        cipher = Cipher.getInstance(AES_PADDING_SCHEME)
        cipher?.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        ivParameterSpec = IvParameterSpec(iv)
    }


    private fun generateSalt(length: Int) = generateKey(length)

    private fun generateIv(length: Int) = generateKey(length)

    private fun generateKey(length: Int): String {
        val salt = StringBuilder()
        val random = SecureRandom()
        while (salt.length < length) {
            val index = (random.nextFloat() * STANDAR_CHARS.length).toInt()
            salt.append(STANDAR_CHARS[index])
        }
        return salt.toString()
    }

    private fun clearData(data: ByteArray): String {
        return String(data).replace("\n", "")
    }

    override fun encrypt(data: String): String {
        cipher?.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        return clearData(Base64.encode(cipher?.doFinal(data.toByteArray()), DEFAULT))
    }

    override fun decrypt(data: String?): String? {
        cipher?.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        return cipher?.doFinal(Base64.decode(data?.toByteArray(), 0))?.let { String(it) }
    }

    override fun encrypt(data: Any): String {
        val plainData = clearDataBreakLines(Gson().toJson(data))
        cipher?.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        return clearData(Base64.encode(cipher?.doFinal(plainData.toByteArray()), DEFAULT))
    }

    override fun decrypt(data: Any?): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun key(): String {
        return this.key
    }

    override fun salt(): String {
        return this.salt
    }

    override fun iv(): String {
        return this.iv?.let { String(it, Charset.defaultCharset()) }.toString()
    }

    private fun clearDataBreakLines(data: String): String {
        return data.replace("\n", "").replace("\n", "")
    }
}