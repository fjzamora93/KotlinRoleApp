package com.unir.roleapp.adventure.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import com.unir.roleapp.adventure.domain.model.Adventure
import com.unir.roleapp.adventure.domain.usecase.CreateAdventureRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdventureRepositoryImpl @Inject constructor() : AdventureRepository {

    private val db: FirebaseFirestore = Firebase.firestore

    override suspend fun createAdventure(request: CreateAdventureRequest): Result<Adventure> {
        return try {
            val doc = db.collection("adventures").document()
            val adv = Adventure(
                id          = doc.id,
                title       = request.title,
                description = request.description,
                createdAt   = System.currentTimeMillis()
            )
            doc.set(adv).await()
            Result.success(adv)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAdventures(): Result<List<Adventure>> {
        return try {
            val snap = db.collection("adventures").get().await()
            val list = snap.documents.mapNotNull { it.toObject(Adventure::class.java) }
            Result.success(list)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAdventure(adventureId: String): Result<Adventure> {
        return try {
            val snap = db
                .collection("adventures")
                .document(adventureId)
                .get()
                .await()
            val adv = snap.toObject(Adventure::class.java)
                ?: return Result.failure(IllegalStateException("No existe aventura"))
            Result.success(adv)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAdventure(adventureId: String) {
        val snap = db
            .collection("adventures")
            .document(adventureId)
            .delete()
            .await()
    }
}
