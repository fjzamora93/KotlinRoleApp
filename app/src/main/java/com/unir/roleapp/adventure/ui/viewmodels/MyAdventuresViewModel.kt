package com.unir.roleapp.adventure.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.roleapp.auth.domain.usecase.user.GetUserUseCase
import com.unir.roleapp.adventure.domain.model.Adventure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MyAdventuresViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): ViewModel() {

    private val _adventures = MutableStateFlow<List<Adventure>>(emptyList())
    val adventures: StateFlow<List<Adventure>> = _adventures.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadMyAdventures()
    }

    private fun loadMyAdventures() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            // 1) Primero saco mi userId
            val uid = getUserUseCase()
                .getOrNull()
                ?.id
                ?.toString()

            if (uid.isNullOrBlank()) {
                _error.value = "Usuario no encontrado"
                _loading.value = false
                return@launch
            }

            // 2) Query a Firestore: collection("adventures").whereEqualTo("userId", uid)
            try {
                val snap = db.collection("adventures")
                    .whereEqualTo("userId", uid)
                    .get()
                    .await()

                val list = snap.documents.mapNotNull { it.toObject(Adventure::class.java) }
                _adventures.value = list
            } catch (e: Exception) {
                _error.value = e.message
            }

            _loading.value = false
        }
    }
}
