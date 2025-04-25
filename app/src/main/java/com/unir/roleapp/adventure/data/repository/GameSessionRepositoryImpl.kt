package com.unir.roleapp.adventure.data.repository

import android.content.SharedPreferences
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.unir.roleapp.adventure.data.model.GameSession
import com.unir.roleapp.adventure.domain.GameSessionRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameSessionRepositoryImpl @Inject constructor(
    private val db: FirebaseFirestore,
    private val userPreferences: UserPreferences
) : GameSessionRepository {


    // Crear un nuevo documento
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

    // Observar cambios de una sesión por ID (DEBE NOTIFICAR TODOS LOS CAMBIOS EN VIVO)
    override suspend fun observeGameSession(sessionId: String): Flow<GameSession> {
        return callbackFlow {
            val listener = db.collection("game_sessions")
                .document(sessionId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val session = snapshot.toObject(GameSession::class.java)
                        if (session != null) {
                            trySend(session).isSuccess
                        }
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    // R - Leer todos los documentos de la colección "game_sessions"
    override suspend fun getGameSessions(): Result<List<GameSession>> {
        return try {
            val email = userPreferences.getEmail()
                ?: return Result.failure(Exception("No se encontró el email del usuario en SharedPreferences"))

            val documents = db.collection("game_sessions")
                .whereEqualTo("userEmail", email)
                .get()
                .await()

            val sessionList = documents.mapNotNull { document ->
                document.toObject(GameSession::class.java)
            }

            Result.success(sessionList)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }


    // Actualizar sesión
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

    // Eliminar sesión
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

