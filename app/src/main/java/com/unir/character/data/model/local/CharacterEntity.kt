package com.unir.character.data.model.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.character.data.model.remote.CharacterRequest

@Entity(tableName = "character_entity_table")
data class CharacterEntity(
    @PrimaryKey var id: Long = 0L,

    // DATOS DE USUARIO Y SESIÓN
    @ColumnInfo(name = "userId")
    var userId: Int,
    var gameSessionId: Int? = null,
    val updatedAt: Long,

    // Datos del personaje
    var name: String = "",
    var description: String = "",
    var rolClass: RolClass = RolClass.WARRIOR,
    var race: Race = Race.HUMAN,
    var armor: Int = 0,
    var age: Int = 23,
    var gold: Int = 50,


    // Stats
    var strength: Int = 10,
    var dexterity: Int = 10,
    var constitution: Int = 10,
    var intelligence: Int = 10,
    var wisdom: Int = 10,  // Educación en Chutlhú
    var charisma: Int = 10,  // Apariencia en Chutlhu

    // Stats derivados (se calculan con la información anterior y NO tienen formulario propio)
    // DE la llamada de CHutlhú (dentro del screen, el sistema de Chutlhu y aquelarre se basan en d100, no en d20)
    var power : Int = 11,  // Cálculo de la séptima edición: 3d6 x 5
    var sanity : Int = 11,
    var currentSanity : Int = sanity,

    // Otros stats calculados
    var hp: Int = 10, // calculada a partir de Constitution y size
    var currentHp: Int = hp,
    var ap: Int = 1, // calculada a partir de inteligencia, sabiduría o pow
    var currentAp: Int = ap,
    var level: Int = 1,

    ){

    // Asignamos el ID único cuando se crea el objeto
    init {
        if (id == 0L) {
            this.id = "$userId${System.currentTimeMillis()}".toLong()
        }
    }

    fun toApiRequest(): CharacterRequest {

        return CharacterRequest(
            id = this.id,
            name = this.name,
            updatedAt = this.updatedAt,
            description = this.description,
            race = this.race.name,
            level = this.level,
            armor = this.armor,
            age = this.age,
            gold = this.gold,
            strength = this.strength,
            dexterity = this.dexterity,
            constitution = this.constitution,
            intelligence = this.intelligence,
            wisdom = this.wisdom,
            charisma = this.charisma,
            imgUrl = null,
            gameSessionId = this.gameSessionId,
            userId = this.userId,
            roleClass = this.rolClass.name,
            skills = emptyList()
        )
    }




}


