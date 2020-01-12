package br.com.aleson.core.tools.coretools.cryptography.aes

import android.util.Base64
import br.com.aleson.core.tools.coretools.cryptography.AES_ALGORITHM
import br.com.aleson.core.tools.coretools.cryptography.AES_KDF
import br.com.aleson.core.tools.coretools.cryptography.AES_PADDING_SCHEME
import br.com.aleson.core.tools.coretools.cryptography.STANDAR_CHARS
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
    private var ivParameterSpec: IvParameterSpec? = IvParameterSpec(generateSalt(8).toByteArray())


    init {
        val key = generateSalt(16)
        val salt = generateSalt(8)
        val factory = SecretKeyFactory.getInstance(AES_KDF)
        val spec = PBEKeySpec(key.toCharArray(), salt.toByteArray(), iterationCount, keyStrength)
        val secretKey = factory.generateSecret(spec)
        secretKeySpec = SecretKeySpec(secretKey.encoded, AES_ALGORITHM)
        cipher = Cipher.getInstance(AES_PADDING_SCHEME)
        cipher?.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        ivParameterSpec = IvParameterSpec(cipher?.iv)
        val iv = cipher?.iv
    }

    private fun generateSalt(length: Int): String {
        val SALTCHARS = STANDAR_CHARS
        val salt = StringBuilder()
        val random = Random(System.currentTimeMillis())
        while (salt.length < length) {
            val index = (random.nextFloat() * SALTCHARS.length).toInt()
            salt.append(SALTCHARS[index])
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

}