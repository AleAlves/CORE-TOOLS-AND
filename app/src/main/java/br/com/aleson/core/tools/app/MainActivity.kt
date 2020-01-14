package br.com.aleson.core.tools.app

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.aleson.core.tools.coretools.CoreToolsBuilder
import br.com.aleson.core.tools.coretools.cryptography.model.PublicKey
import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val core = CoreToolsBuilder.Builder()
            .server("https://base-node-typescript-api.herokuapp.com/")
            .build()

        core.retrofit?.request()?.create(GetRequest::class.java)?.getMethod()?.enqueue(object : Callback<Version> {

            override fun onFailure(call: Call<Version>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Version>, response: Response<Version>) {
                //Toast.makeText(applicationContext, "Sucess version: "+ (response.body() as Version)?.version, Toast.LENGTH_LONG).show()
            }

        })

        val encrypted = core.crypto.AES().encrypt("wow")
        val plainData = core.crypto.AES().decrypt(encrypted)
        Log.d("CORE_LOG", "$encrypted - $plainData")

        core.retrofit?.request()?.create(PublicKeyRequest::class.java)?.getPublicKey()
            ?.enqueue(object : Callback<HTTPResponse<PublicKey>> {
                override fun onFailure(call: Call<HTTPResponse<PublicKey>>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<HTTPResponse<PublicKey>>,
                    response: Response<HTTPResponse<PublicKey>>
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Sucess RSA: " + (response.body()?.data as PublicKey).publicKey,
                        Toast.LENGTH_LONG
                    ).show()
                    val rsaEncryptes = core.crypto.RSA((response.body()?.data as PublicKey).publicKey)?.encrypt("wow")
                    Toast.makeText(
                        applicationContext,
                        "RSA version: $rsaEncryptes",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}
