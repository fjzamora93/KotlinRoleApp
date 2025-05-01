package com.unir.roleapp.character.data.model.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.unir.roleapp.character.data.model.remote.CharacterRequest

@Entity(tableName = "character_entity_table")
data class CharacterEntity(
    @PrimaryKey var id: Long = 0L,

    // DATOS DE USUARIO Y SESIÓN
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
    var gameSessionId: Int = 0,
    var updatedAt: Long = 0L,

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
    var wisdom: Int = 10,
    var charisma: Int = 10,

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

    var imgUrl: String = "",

    @ColumnInfo(name = "_level")
    var _level: Int = 1,

    ){


    @get:Ignore
    var level: Int
        get() = _level
        set(value) {
            if (value > 0) {  // Evita valores negativos o cero
                _level = value
            }
        }


    fun toApiRequest(): CharacterRequest {

        return CharacterRequest(
            id = this.id,
            name = this.name,
            updatedAt = this.updatedAt,
            description = this.description,
            race = this.race.name,
            level = this._level,
            armor = this.armor,
            age = this.age,
            gold = this.gold,
            strength = this.strength,
            dexterity = this.dexterity,
            constitution = this.constitution,
            intelligence = this.intelligence,
            wisdom = this.wisdom,
            charisma = this.charisma,

            hp = this.hp,
            currentHp = this.currentHp,
            ap = this.ap,
            currentAp = this.currentAp,

            imgUrl = this.imgUrl,
            gameSessionId = this.gameSessionId,
            userId = this.userId,
            roleClass = this.rolClass.name,
            characterSkills = emptyList()
        )
    }
}


