package com.unir.roleapp.adventure.ai.service
import com.unir.roleapp.adventure.ai.model.StableDiffusionRequest
import com.unir.roleapp.adventure.ai.model.StableDiffusionResponse
import com.unir.roleapp.adventure.ai.network.RetrofitInstance

class StableDiffusionApiClient {
    private val api = RetrofitInstance.retrofit.create(StableDiffusionApiService::class.java)

    suspend fun generateImage(prompt: String): StableDiffusionResponse {
        android.util.Log.d("StableDiffusionApiClient", "Enviando solicitud con prompt: $prompt")
        val request = StableDiffusionRequest(prompt = prompt)
        return api.generateImage(request)
    }
}
