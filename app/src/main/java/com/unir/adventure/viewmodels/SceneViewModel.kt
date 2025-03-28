package com.unir.adventure.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.unir.adventure.data.model.Scene
import com.unir.adventure.data.repository.SceneRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SceneViewModel @Inject constructor(
    private val sceneRepository: SceneRepository // Inyectamos el repositorio
) : ViewModel() {

    private val _scene = MutableStateFlow<List<Scene>>(emptyList())
    val scene: StateFlow<List<Scene>> = _scene

    init {
        loadData()
    }

    // Método para agregar una nueva escena
    fun addScene(scene: Scene) {
        _scene.value = emptyList() // Opcional: se puede mostrar un estado vacío mientras se realiza la operación
        viewModelScope.launch {
            val result = sceneRepository.addScene(scene)
            println(result)
        }
    }

    // Método para cargar los datos
    private fun loadData() {
        viewModelScope.launch {
            val result = sceneRepository.getScenes()
            result.onSuccess { scenes ->
                _scene.value = scenes
                println(scenes)
                Log.d("SceneViewModel", "Datos cargados correctamente ${_scene.value}")
            }.onFailure { exception ->
                Log.e("SceneViewModel", "Error al cargar los datos $exception")
            }

        }
    }
}
