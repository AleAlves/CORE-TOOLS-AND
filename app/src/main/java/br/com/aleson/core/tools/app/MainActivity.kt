package br.com.aleson.core.tools.app

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.aleson.core.tools.coretools.CoreToolsBuilder
import br.com.aleson.core.tools.coretools.cryptography.model.PublicKey
import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPRequest
import br.com.aleson.core.tools.coretools.retrofit.domain.HTTPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var core: CoreToolsBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        core = CoreToolsBuilder.Builder()
            .server("http://192.168.0.18:8084/")
            .build()

        this.detectOpenGLES20()

        val encrypted = core.crypto.AES().encrypt("wow")
        val plainData = core.crypto.AES().decrypt(encrypted)

        findViewById<Button>(R.id.button).setOnClickListener {
            getPublicKey(object : PublicKeyCallback {

                override fun onLoad(publicKey: String) {
                    getTicket(publicKey, object : ticketCallback {
                        override fun onLoad(ticket: String) {
                            login(ticket)
                        }

                    })
                }

            })
        }

    }

    private fun detectOpenGLES20() {
        val result: Int
        val activityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configInfo = activityManager.deviceConfigurationInfo
        result = if (configInfo.reqGlEsVersion != ConfigurationInfo.GL_ES_VERSION_UNDEFINED) {
            configInfo.reqGlEsVersion
        } else {
            1 shl 16 // Lack of property means OpenGL ES version 1
        }

        Log.e("reqGlEsVersion", result.toString())
        Log.e("getGlEsVersion", configInfo.glEsVersion)
    }

    interface PublicKeyCallback {

        fun onLoad(rsaData: String)
    }

    interface ticketCallback {

        fun onLoad(ticket: String)
    }

    private fun getVersion() {
        core.retrofit?.request()?.create(GetRequest::class.java)?.getMethod()?.enqueue(object : Callback<Version> {

            override fun onFailure(call: Call<Version>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Version>, response: Response<Version>) {
                //Toast.makeText(applicationContext, "Sucess version: "+ (response.body() as Version)?.version, Toast.LENGTH_LONG).show()
            }

        })
    }


    private fun getPublicKey(callback: PublicKeyCallback) {
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
                    val rsaEncryptes =
                        core.crypto.RSA((response.body()?.data as PublicKey).publicKey).encrypt("wow")
                    Toast.makeText(
                        applicationContext,
                        "RSA version: $rsaEncryptes",
                        Toast.LENGTH_LONG
                    ).show()
                    callback.onLoad((response.body()?.data as PublicKey).publicKey)
                }
            })
    }

    private fun getTicket(key: String, callback: ticketCallback) {

        var keyChain = KeyChain(
            "uid",
            core.crypto.AES().key(),
            core.crypto.AES().salt(),
            core.crypto.AES().iv()
        )

        val request = HTTPRequest(core.crypto.RSA(key).encrypt(keyChain))

        core.retrofit?.request()?.create(GenerateAccessToken::class.java)?.token(request)?.enqueue(

            object : Callback<HTTPResponse<AccessToken>> {
                override fun onFailure(call: Call<HTTPResponse<AccessToken>>, t: Throwable) {
                    Log.d("", "")
                }

                override fun onResponse(
                    call: Call<HTTPResponse<AccessToken>>,
                    response: Response<HTTPResponse<AccessToken>>
                ) {
                    Log.d("", "")
                    callback.onLoad((response.body()?.data as AccessToken).accessToken)
                }

            }
        )
    }

    private fun login(accessToken: String) {
        val user = User("Aleson", "aleson.a.s@mail.com", "www.img.com", "123")

        val request = HTTPRequest(core.crypto.AES().encrypt(user))


        core.retrofit?.request()?.create(LoginRequest::class.java)?.login(accessToken, request)?.enqueue(
            object : Callback<HTTPResponse<SessionToken>> {

                override fun onFailure(call: Call<HTTPResponse<SessionToken>>, t: Throwable) {
                    Log.d("", "")
                }

                override fun onResponse(
                    call: Call<HTTPResponse<SessionToken>>,
                    response: Response<HTTPResponse<SessionToken>>
                ) {
                    Log.d("", "")
                }
            })
    }
}
