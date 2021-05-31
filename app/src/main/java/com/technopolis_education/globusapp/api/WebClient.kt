package com.technopolis_education.globusapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WebClient {
    private val BASE_URL = "http://192.168.0.5:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: WebServices = retrofit.create(WebServices::class.java)

    fun getApi() = api
}