package com.devskar.keystore

/**
 * @author Oscar Díaz
 * @version 1, 2021/09/05
 *
 * El código forma parte del respositorio "AndroidKotlin"
 * Github: https://github.com/OscarD241/AndroidKotlin
 *
 */

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyProperties
import android.security.keystore.KeyProtection
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.nio.charset.Charset
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class MainActivity : AppCompatActivity() {

    private val TAG = "[Main]"

    private val TYPE = "AndroidKeyStore" //tipo de la instancia del keystore
    private val ALIAS = "key1" //alias de la llave en el keyStore

    private val KEYSIZE = 256 //tamaño de la llave

    private var iv: ByteArray? = null //vector de inicialización
    private lateinit var transform: String //modo de cifrado
    private lateinit var algoritmo: String
    private lateinit var blockMode: String
    private lateinit var padding: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            algoritmo = KeyProperties.KEY_ALGORITHM_AES
            blockMode = KeyProperties.BLOCK_MODE_CBC
            padding = KeyProperties.ENCRYPTION_PADDING_PKCS7
            transform = "$algoritmo/$blockMode/$padding"

            val key = generateKey()
            loadKey(key)
            val keyFromKs = getKeyFromKeyStore()
            val original = "Hola Mundo"
            val cifrado = encrypt(keyFromKs, original)
            val descifrado = decrypt(keyFromKs, cifrado)
            Toast.makeText(this, descifrado, Toast.LENGTH_LONG).show()
        }
    }

    private fun generateKey(): SecretKey{
        val keygen = KeyGenerator.getInstance(algoritmo)
        keygen.init(KEYSIZE)
        return keygen.generateKey()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun loadKey(key: SecretKey){
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

    private fun getKeyFromKeyStore(): SecretKey{
        val keyStore = KeyStore.getInstance(TYPE)
        keyStore.load(null)
        return keyStore.getKey(ALIAS, null) as SecretKey
    }

    private fun encrypt(key: SecretKey, value: String) = try {
        val cipher = Cipher.getInstance(transform)
        cipher.init(Cipher.ENCRYPT_MODE, key, SecureRandom())
        iv = cipher.iv
        cipher.doFinal(value.toByteArray())
    }
    catch (e: Throwable){
        Log.e(TAG, "Exception: $e")
        null
    }

    private fun decrypt(key: SecretKey, info: ByteArray?) = try {
        val cipher = Cipher.getInstance(transform)
        cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(iv))
        cipher.doFinal(info).toString(Charset.defaultCharset())
    }
    catch (e: Throwable){
        Log.e(TAG, "Exception: $e")
        null
    }

}