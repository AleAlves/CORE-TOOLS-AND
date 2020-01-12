package br.com.aleson.core.tools.coretools.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCore constructor(private val url: String) {

    private var retrofit: Retrofit? = null

    fun request() : Retrofit? {
        val httpClient = OkHttpClient.Builder()
        val gson = GsonBuilder().setLenient().create()
        val builder = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson))
        this.retrofit = builder.client(httpClient.build()).build()
        return this.retrofit
    }
}