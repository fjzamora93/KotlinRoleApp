package com.unir.roleapp.adventure.domain

import com.google.firebase.firestore.DocumentReference
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.unir.roleapp.adventure.data.model.GameSession

interface GameSessionRepository {
    suspend fun addGameSession(session: GameSession): Result<DocumentReference>
    suspend fun getGameSessionById(sessionId: String): Result<GameSession> // <-- nuevo
    suspend fun getGameSessions(): Result<List<GameSession>>
    suspend fun updateGameSession(sessionId: String, updatedSession: GameSession): Result<Void>
    suspend fun deleteGameSession(sessionId: String): Result<Void>
}
