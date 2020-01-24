package br.com.aleson.core.tools.coretools.cryptography.aes

import android.util.Base64
import br.com.aleson.core.tools.coretools.cryptography.AES_ALGORITHM
import br.com.aleson.core.tools.coretools.cryptography.AES_KDF
import br.com.aleson.core.tools.coretools.cryptography.AES_PADDING_SCHEME
import br.com.aleson.core.tools.coretools.cryptography.STANDAR_CHARS
import com.google.gson.Gson
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class AESImpl : AES {

    private var cipher: Cipher? = null
    private var secretKeySpec: SecretKeySpec? = null
    private val iterationCount = 2048
    private val keyStrength = 256
    private var ivParameterSpec: IvParameterSpec? = null
    private var key: String
    private var salt: String
    private var iv: ByteArray?

    init {
        key = generateSalt(16)
        salt = generateSalt(8)
        val factory = SecretKeyFactory.getInstance(AES_KDF)
        val spec = PBEKeySpec(key.toCharArray(), salt.toByteArray(), iterationCount, keyStrength)
        val secretKey = factory.generateSecret(spec)
        secretKeySpec = SecretKeySpec(secretKey.encoded, AES_ALGORITHM)
        cipher = Cipher.getInstance(AES_PADDING_SCHEME)
        cipher?.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        ivParameterSpec = IvParameterSpec(cipher?.iv)
        iv =  ivParameterSpec!!.iv as ByteArray
    }

    private fun generateSalt(length: Int): String {
        val salt = StringBuilder()
        val random = Random(System.currentTimeMillis())
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
        return clearData(Base64.encode(cipher?.doFinal(data.toByteArray()), Base64.DEFAULT))
    }

    override fun decrypt(data: String?): String? {
        cipher?.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        return cipher?.doFinal(Base64.decode(data?.toByteArray(), 0))?.let { String(it) }
    }

    override fun encrypt(data: Any): String {
        val plainData = clearDataBreakLines(Gson().toJson(data))
        cipher?.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
        return clearData(Base64.encode(cipher?.doFinal(plainData.toByteArray()), Base64.DEFAULT))
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
        return Base64.encodeToString(this.iv, 0)
    }

    private fun clearDataBreakLines(data: String): String {
        return data.replace("\n", "").replace("\n", "")
    }
}