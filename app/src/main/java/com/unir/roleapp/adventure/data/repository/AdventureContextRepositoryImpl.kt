package com.unir.roleapp.adventure.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.unir.roleapp.adventure.domain.model.CharacterContext
import com.unir.roleapp.adventure.domain.usecase.SaveAdventureContextUseCase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdventureContextRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : SaveAdventureContextUseCase {

    override suspend fun invoke(
        adventureId: String,
        historicalContext: String,
        characterContexts: List<CharacterContext>
    ): Result<Unit> {
        return try {
            val doc = firestore.collection("games").document(adventureId)
            doc.update(
                mapOf(
                    "historicalContext" to historicalContext,
                    "characterContexts" to characterContexts
                )
            ).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}