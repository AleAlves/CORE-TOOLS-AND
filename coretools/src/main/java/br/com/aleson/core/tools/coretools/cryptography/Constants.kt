package br.com.aleson.core.tools.coretools.cryptography


const val AES_KDF = "PBKDF2WithHmacSHA1"
const val RSA_KDF = "RSA"
const val AES_ALGORITHM = "AES"
const val RSA_PADDING_SCHEME = "RSA/ECB/PKCS1Padding"
const val AES_PADDING_SCHEME = "AES/CBC/PKCS5Padding"
const val STANDAR_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890"
val PUBLIC_KEY_CLEN_REGEX = "(-+BEGIN PUBLIC KEY-+\\r?\\n|-+END PUBLIC KEY-+\\r?\\n?)".toRegex()