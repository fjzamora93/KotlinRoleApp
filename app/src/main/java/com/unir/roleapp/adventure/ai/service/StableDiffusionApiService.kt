package com.unir.roleapp.adventure.ai.service
import com.unir.roleapp.adventure.ai.model.StableDiffusionRequest
import com.unir.roleapp.adventure.ai.model.StableDiffusionResponse
import retrofit2.http.POST
import retrofit2.http.Body

interface StableDiffusionApiService {

    @POST("/sdapi/v1/txt2img")
    suspend fun generateImage(@Body prompt: StableDiffusionRequest): StableDiffusionResponse
}
