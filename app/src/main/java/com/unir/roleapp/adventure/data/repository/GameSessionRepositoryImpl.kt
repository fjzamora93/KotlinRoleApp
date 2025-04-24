package com.unir.roleapp.adventure.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.unir.roleapp.adventure.data.model.GameSession
import com.unir.roleapp.adventure.domain.GameSessionRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class GameSessionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore
) : GameSessionRepository {


    // C - Crear un nuevo documento en la colección "game_sessions"
    override suspend fun addGameSession(session: GameSession): Result<DocumentReference> {
        return try {
            val result = db.collection("game_sessions")
                .add(session)
                .await()
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }


    override suspend fun getGameSessionById(sessionId: String): Result<GameSession> {
        return try {
            val document = db.collection("game_sessions")
                .document(sessionId)
                .get()
                .await()

            if (document.exists()) {
                val session = GameSession(
                    id = document.getLong("id") ?: "",
                    )
                Result.success(session)
            } else {
                Result.failure(Exception("La sesión no existe"))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }


    // R - Leer todos los documentos de la colección "game_sessions"
    override suspend fun getGameSessions(): Result<List<GameSession>> {
        return try {
            val documents = db.collection("game_sessions")
                .get()
                .await()

            val sessionList = documents.map { document ->
                GameSession(
                    id = document.getLong("id") ?: "",
                )
            }
            Result.success(sessionList)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    // U - Actualizar un documento existente en la colección "game_sessions"
    override suspend fun updateGameSession(sessionId: String, updatedSession: GameSession): Result<Void> {
        return try {
            val result = db.collection("game_sessions")
                .document(sessionId)
                .set(updatedSession)
                .await()
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    // D - Eliminar un documento de la colección "game_sessions"
    override suspend fun deleteGameSession(sessionId: String): Result<Void> {
        return try {
            val result = db.collection("game_sessions")
                .document(sessionId)
                .delete()
                .await()
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
