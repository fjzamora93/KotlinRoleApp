package com.unir.adventure.data.repository

import com.MyApplication.Companion.context
import com.data.FirebaseConfigManager
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.unir.adventure.data.model.Scene
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


/** Repositorio para acceder a los datos... Ahora mismo la lógica está desplazada al ViewModel */
//TODO : Refactorizar y traer aquí el acceso a los datos de Firebase
class SceneRepository @Inject constructor(){

    private val database = Firebase.database
    private var db: FirebaseFirestore = Firebase.firestore



    // C - Crear un nuevo documento en la colección "scene"
    suspend fun addScene(scene: Scene): Result<DocumentReference> {
        return try {
            val result = db.collection("scene")
                .add(scene)
                .await() // Suspende la ejecución hasta obtener el resultado
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    // R - Leer todos los documentos de la colección "scene"
    suspend fun getScenes(): Result<List<Scene>> {
        return try {
            val documents = db.collection("scene")
                .get()
                .await() // Suspende la ejecución hasta obtener los documentos

            val scenesList = documents.map { document ->
                Scene(
                    name = document.getString("name") ?: "",
                    description = document.getString("description") ?: ""
                )
            }
            Result.success(scenesList)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    // U - Actualizar un documento existente en la colección "scene"
    suspend fun updateScene(sceneId: String, updatedScene: Scene): Result<Void> {
        return try {
            val result = db.collection("scene")
                .document(sceneId)
                .set(updatedScene)
                .await() // Suspende la ejecución hasta que la operación se complete
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    // D - Eliminar un documento de la colección "scene"
    suspend fun deleteScene(sceneId: String): Result<Void> {
        return try {
            val result = db.collection("scene")
                .document(sceneId)
                .delete()
                .await() // Suspende la ejecución hasta completar la eliminación
            Result.success(result)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
