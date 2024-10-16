/*ApiService*/
package com.example.pokeradvisorapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openAiApiService: OpenAiApiService = retrofit.create(OpenAiApiService::class.java)
}
