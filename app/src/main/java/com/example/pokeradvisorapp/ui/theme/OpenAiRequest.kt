/*OpenAiRequest*/
package com.example.pokeradvisorapp.ui.theme


data class OpenAiRequest(
    val model: String = "gpt-4", // Modèle GPT4
    val messages: List<Message>,
    val max_tokens: Int = 150,
    val temperature: Double = 0.7
)

data class Message(
    val role: String,
    val content: String
)