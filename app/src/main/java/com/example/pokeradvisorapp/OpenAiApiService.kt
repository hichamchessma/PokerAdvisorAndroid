package com.example.pokeradvisorapp

import com.example.pokeradvisorapp.ui.theme.OpenAiRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OpenAiApiService {
    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer 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" // Remplace par ta clé API OpenAI
    )
    @POST("v1/chat/completions")
    fun getChatCompletion(@Body request: OpenAiRequest): Call<OpenAiResponse>
}