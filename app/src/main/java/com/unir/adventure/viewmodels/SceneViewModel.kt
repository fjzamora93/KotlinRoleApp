package com.unir.adventure.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.adventure.data.model.Scene
import com.unir.adventure.data.repository.SceneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SceneViewModel @Inject constructor(
    private val sceneRepository: SceneRepository // Inyectamos el repositorio
) : ViewModel() {

    private val _scenes = MutableStateFlow<List<Scene>>(emptyList())
    val scenes: StateFlow<List<Scene>> = _scenes

    private val _currentScene = MutableStateFlow<Scene?>(null)
    val currentScene: StateFlow<Scene?> = _currentScene


    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    init {
        loadData()
    }

    // SOLO PARA TEST: añade un nuevo escenario a Firebase
    fun addScene(scene: Scene) {
        _scenes.value = emptyList() // Opcional: se puede mostrar un estado vacío mientras se realiza la operación
        viewModelScope.launch {
            val result = sceneRepository.addScene(scene)
            println(result)
        }
    }

    // Cargar todos los escenarios de la colección
    private fun loadData() {
        viewModelScope.launch {
            val result = sceneRepository.getScenes()
            result.onSuccess { scenes ->
                _scenes.value = scenes
                _currentScene.value = scenes.lastOrNull()
                println(scenes)
                println("Escenario actual: " + _currentScene.value)

                Log.d("SceneViewModel", "Datos cargados correctamente ${_scenes.value}")
            }.onFailure { exception ->
                _errorMessage.value = "Error al cargar los datos $exception"
                Log.e("SceneViewModel", "Error al cargar los datos $exception")
            }

        }
    }
}
