package com.unir.roleapp.adventure.ai.model

data class StableDiffusionRequest(
    val prompt: String,
    val steps: Int = 20,
    val width: Int = 512,
    val height: Int = 512,
    val cfg_scale: Float = 7.0f,
    val sampler_index: String = "Euler a"
)
