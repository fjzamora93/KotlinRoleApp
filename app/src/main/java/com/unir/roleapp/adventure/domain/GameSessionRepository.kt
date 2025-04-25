package com.unir.roleapp.adventure.domain

import com.google.firebase.firestore.DocumentReference
import com.roleapp.character.data.model.local.CharacterEntity
import com.roleapp.character.data.model.local.CharacterSkillCrossRef
import com.unir.roleapp.adventure.data.model.GameSession
import kotlinx.coroutines.flow.Flow

interface GameSessionRepository {
    suspend fun observeGameSession(sessionId: String): Flow<GameSession>
    suspend fun getGameSessions(): Result<List<GameSession>>
    suspend fun addGameSession(session: GameSession): Result<DocumentReference>

    suspend fun updateGameSession(sessionId: String, updatedSession: GameSession): Result<Void>
    suspend fun deleteGameSession(sessionId: String): Result<Void>
}
