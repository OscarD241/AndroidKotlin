package com.devskar.keystore.adapters

/**
* @author Oscar Díaz
* @version 1.1, 2021/09/05
*
* El código forma parte del respositorio "AndroidKotlin"
* Github: https://github.com/OscarD241/AndroidKotlin
*
*/

import android.os.Build
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Log
import androidx.annotation.RequiresApi
import java.nio.charset.Charset
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

/**
 * @param algorithm: algoritmo con el que se cifrará/descifrará la información
 * @param blockMode: modo de bloques para el algoritmo
 * @param padding: padding para el algoritmo
 * @param keySize: tamaño de la llave simétrica a crear, por defecto 256
 */
@RequiresApi(Build.VERSION_CODES.M)
class KeyStoreAdapter(
    val algorithm: String,
    val blockMode: String,
    val padding: String,
    val keySize: Int = 256) {

    private val TAG = "[KeyStoreAdapter]" //Etiqueta para el log

    private val TYPE = "AndroidKeyStore" //tipo de la instancia del keystore
    private val ALIAS = "key1" //alias de la llave en el keyStore

    private var iv: ByteArray? = null //vector de inicialización
    private val transform = "$algorithm/$blockMode/$padding"

    /**
     * Genera una llave simétrica para cifrado [algoritmo], con tamaño de [KEYSIZE]
     * @return SecretKey
     */
    fun generateKey(): SecretKey {
        val keygen = KeyGenerator.getInstance(algorithm)
        keygen.init(keySize)
        return keygen.generateKey()
    }

    /**
     * Almacena la llave generada dentro del keyStore [TYPE]
     * @param key: llave a guardar en el keyStore
     */
    fun loadKey(key: SecretKey){
        val keyStore = KeyStore.getInstance(TYPE)
        keyStore.load(null)
        keyStore.setEntry(
            ALIAS,
            KeyStore.SecretKeyEntry(key),
            KeyProtection.Builder(KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(blockMode)
                .setEncryptionPaddings(padding)
                .build()
        )
    }

    /**
     * Obtiene la instancia hacia la llave almacenada en el keyStore
     * @return SecretKey
     */
    fun getKeyFromKeyStore(): SecretKey {
        val keyStore = KeyStore.getInstance(TYPE)
        keyStore.load(null)
        return keyStore.getKey(ALIAS, null) as SecretKey
    }

    /**
     * Cifra una cadena de texto utilizando una llave
     * @param key: llave para cifrar la información
     * @param value: cadena a cifrar
     * @return ByteArray si se cifra, null si no se cifra
     */
    fun encrypt(key: SecretKey, value: String) = try {
        val cipher = Cipher.getInstance(transform)
        cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom())
        iv = cipher.iv
        cipher.doFinal(value.toByteArray())
    }
    catch (e: Throwable){
        Log.e(TAG, "Exception: $e")
        null
    }

    /**
     * Descifra un arreglo de bytes y lo convierte a String
     * @param key: llave para descifrar la información
     * @param info: información a descifrar
     * @return String si se descifra, null si no se descifra
     */
    fun decrypt(key: SecretKey, info: ByteArray?) = try {
        val cipher = Cipher.getInstance(transform)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        cipher.doFinal(info).toString(Charset.defaultCharset())
    }
    catch (e: Throwable){
        Log.e(TAG, "Exception: $e")
        null
    }

}