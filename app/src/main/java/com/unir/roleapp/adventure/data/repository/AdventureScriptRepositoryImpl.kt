package com.unir.roleapp.adventure.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unir.roleapp.adventure.domain.model.AdventureAct
import com.unir.roleapp.adventure.domain.model.AdventureSetup
import com.unir.roleapp.adventure.domain.usecase.GenerateAdventureScriptUseCase
import com.unir.roleapp.adventure.domain.usecase.SaveAdventureUseCase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdventureScriptRepositoryImpl @Inject constructor() :
    GenerateAdventureScriptUseCase,
    SaveAdventureUseCase
{
    private val db: FirebaseFirestore = Firebase.firestore

    // 1️⃣ Generar guion (por ahora stub)
    override suspend fun invoke(setup: AdventureSetup): Result<List<AdventureAct>> {
        // TODO: aquí tu llamada a n8n/LLM
        return Result.success(emptyList())
    }

    // 2️⃣ Guardar actos en Firestore
    override suspend fun invoke(adventureId: String, acts: List<AdventureAct>): Result<Unit> {
        return try {
            db.collection("adventures")
                .document(adventureId)
                .update("acts", acts)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}