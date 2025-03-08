### Estrategia de sincronización

Necestio una estrategia para sincronizar mi aplicacion de Android con su base de datos local en room con la API remota conectada a una base de datos postgres.
Las tablas principales de la base de datos son:
-user (con varios character)
-game_Session (con varios character)
-character 
-charactem_item
-item

La funcionalidad principal de la aplicación es que un usuario pueda crear personajes en su aplicación de android a nivel local y pueda consumir datos de la api remota (adquirir objetos, unirse a una sesión, etc). La desafío consiste en diseñar cuál es la estrategia para sincronizar los datos dado que existe una copia de respaldo del personaje en la base de datos remota. 

UNa idea está clara: la relación character_item y game_session, puesto que involucran a varios personajes en varios dispositivos, requieren conexión a internet y deben ser remotas, aunque también tendrán su copia local para asegurar que los screens cargan de manera fluida mientras se sincronizan los datos remotos. 

Diséñame una buena estrategia para manejar la situación.


## Arquitectura base

graph TD
    A[UI] --> B[ViewModel]
    B --> C[Repository]
    C --> D[Local DB - Room]
    C --> E[Remote API]
    C --> F[Sync Manager]
    F --> G[Conflict Resolver]
    F --> H[Offline Queue]
    
    
## 2. CLasificación de datos y estrategia por tabla

| Tabla          | Tipo de Sincronización              | Prioridad | Conflict Resolution            | Cache Strategy         |
|---------------|-----------------------------------|-----------|--------------------------------|-------------------------|
| user         | Bidireccional (Push-Pull)         | Alta      | Última modificación           | Cache con TTL 1h       |
| character    | Bidireccional con pref. local     | Crítica   | Versión móvil prevalece       | Cache permanente       |
| game_session | Unidireccional (Pull)            | Media     | Server prevalece              | Cache con TTL 15min    |
| character_item | Unidireccional (Push)          | Alta      | Merge inteligente             | Cache temporal         |
| item         | Unidireccional (Pull)            | Baja      | Server prevalece              | Cache con TTL 24h      |



## 3. Mecanismo de sincronización


### 3.1 Offiline-First con cola de operaciones

IMplementar una cola local -Room Table- pending_operations que almacene lo siguiente:
```
@Entity
data class PendingOperation(
    @PrimaryKey val id: String,
    val operationType: String, // "CREATE_CHARACTER", "JOIN_SESSION", etc.
    val localEntityId: String,
    val serializedData: String,
    val timestamp: Long,
    val retryCount: Int = 0
)
```

### 3.2 Estrategia de Sync por Tabla:

Characters:

    Creación local → insertar en Room y añadir a pending_operations

    Al sincronizar:
    
```
suspend fun syncCharacter(localChar: Character) {
    try {
        val remoteChar = api.postCharacter(localChar.toRemoteModel())
        // Merge server-generated fields (ID, timestamps)
        localDb.update(remoteChar.toLocalModel()) 
    } catch (e: Exception) {
        handleSyncError(e, localChar)
    }
}
```

Game Sessions:

    Usar Refresh-on-Focus:

```
fun getGameSessions() = flow {
     emit(localDb.getSessions()) // Emit cached first
     if(hasInternet()) {
         val remote = api.getSessions()
         localDb.insertAll(remote)
         emit(remote)
     }
 }
```

Character Items:

    Sync delta changes usando timestamp:


```
suspend fun syncItems(characterId: String) {
     val lastSync = prefs.getLastItemSync(characterId)
     val changes = api.getItemChangesSince(characterId, lastSync)
     localDb.applyChangesWithTransaction(changes)
 }
```


## 4. Manejo de conflictos

Implementar un ConflictResolver que maneje:

```
fun resolveCharacterConflict(local: Character, remote: Character): Character {
     return when {
         local.version > remote.version -> local
         remote.deleted -> markLocalAsDeleted()
         else -> remote.copy(
             inventory = mergeInventories(local, remote)
         )
     }
 }
```


## 5. Estrategia de Cache

Usar RemoteMediator para paginación
Implementar TTL por tabla:
```
fun shouldInvalidateCache(lastUpdated: Long, ttl: Long): Boolean {
     return System.currentTimeMillis() - lastUpdated > ttl
 }
 
```



## 6. Sincronización en Tiempo Real (Opcional)


Para game_sessions:

    Implementar WebSockets o Firebase Realtime Database para:


```
webSocketListener.onSessionUpdate { updatedSession ->
     localDb.insert(updatedSession)
     notifyUI()
 }
```

## 7. Monitoreo de Estado

Implementar:

    BroadcastReceiver para cambios de conectividad

    Estado de sincronización global:

```
sealed class SyncState {
     object Idle : SyncState()
     data class Syncing(val pendingOps: Int) : SyncState()
     data class Error(val lastError: Exception) : SyncState()
 }
```

## 8. Seguridad y Optimización

Usar Differential Sync para grandes datasets:

```
api.getCharacterChanges(lastSyncDate)
```


## 9. Testing Strategy

    Implementar modo debug con:

        Sync throttling

        Manual trigger de conflictos

        Mock de estados de red

    Unit tests para:


```
test("Local character creation propagates to remote")
test("Server conflict overrides local changes when version is higher")
test("Offline operations queue persists after reboot")
```


