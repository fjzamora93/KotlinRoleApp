package com.unir.roleapp.adventure.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.unir.roleapp.adventure.domain.usecase.DeleteAdventureUseCase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DeleteAdventureUseCaseImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : DeleteAdventureUseCase {
    override suspend fun invoke(adventureId: String): Result<Unit> {
        return try {
            firestore.collection("adventures")
                .document(adventureId)
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}