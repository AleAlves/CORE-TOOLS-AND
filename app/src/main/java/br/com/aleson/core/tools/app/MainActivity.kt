package br.com.aleson.core.tools.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.aleson.core.tools.coretools.CoreToolsBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val core = CoreToolsBuilder.Builder()
            .server("https://base-node-api.herokuapp.com/")
            .build()

        core.retrofit?.request()?.create(GetRequest::class.java)?.getMethod()?.enqueue(object : Callback<Version> {

            override fun onFailure(call: Call<Version>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Version>, response: Response<Version>) {
                Toast.makeText(applicationContext, "Sucess version: "+ (response.body() as Version)?.version, Toast.LENGTH_LONG).show()
            }

        })
    }
}
