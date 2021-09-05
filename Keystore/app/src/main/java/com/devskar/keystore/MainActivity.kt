package com.devskar.keystore

/**
 * @author Oscar Díaz
 * @version 1.1, 2021/09/05
 *
 * El código forma parte del respositorio "AndroidKotlin"
 * Github: https://github.com/OscarD241/AndroidKotlin
 *
 */

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.security.keystore.KeyProperties
import android.widget.Toast
import com.devskar.keystore.adapters.KeyStoreAdapter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val original = "Hola Mundo" //cadena a cifrar

            val algoritmo = KeyProperties.KEY_ALGORITHM_AES //algoritmo para cifrado
            val blockMode = KeyProperties.BLOCK_MODE_CBC //modo de bloques
            val padding = KeyProperties.ENCRYPTION_PADDING_PKCS7 //padding para el algoritmo
            val keySize = 256

            val ksAdapter = KeyStoreAdapter(algoritmo, blockMode, padding, keySize)

            val key = ksAdapter.generateKey()
            ksAdapter.loadKey(key)
            val keyFromKs = ksAdapter.getKeyFromKeyStore()
            val cifrado = ksAdapter.encrypt(keyFromKs, original)
            val descifrado = ksAdapter.decrypt(keyFromKs, cifrado)
            Toast.makeText(this, descifrado, Toast.LENGTH_LONG).show()
        }
    }

}