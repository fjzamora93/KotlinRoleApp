package com.unir.character.data.model.local


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.unir.character.data.model.remote.CharacterRequest

@Entity(tableName = "character_entity_table")
data class CharacterEntity(
    @PrimaryKey var id: Long = 0L,

    // DATOS DE USUARIO Y SESIÓN
    @ColumnInfo(name = "userId")
    var userId: Int = 0,
    var gameSessionId: Int? = null,
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

    @ColumnInfo(name = "_level")
    var _level: Int = 1,


    ){


    @get:Ignore
    var level: Int
        get() = _level
        set(value) {
            if (value > 0) {  // Evita valores negativos o cero
                _level = value
                recalculateStats()
            }
        }

    private fun recalculateStats() {
        hp = 10 + (_level * 2)  // Ejemplo: aumenta 2 puntos de vida por nivel
        ap = 1 + (_level / 5)    // Ejemplo: cada 5 niveles, gana 1 punto de acción extra
    }

    private fun calculateHealthPoints(): Int {
        val baseHealth = when (rolClass) {
            RolClass.WARRIOR, RolClass.BARBARIAN -> 12
            RolClass.PALADIN,  RolClass.EXPLORER, RolClass.CLERIC -> 10
            RolClass.ROGUE,  RolClass.DRUID, RolClass.WARLOCK -> 8
            RolClass.WIZARD, RolClass.BARD -> 6
            else -> 6
        }
        val constitutionBonus = (constitution - 10) / 2
        val raceBonus = when (race) {
            Race.DWARF, Race.ORC -> 2
            Race.DRAGONBORN, Race.HUMAN -> 1
            Race.ELF, Race.HALFLING -> -1
            else -> 0
        }
        return (baseHealth + constitutionBonus + raceBonus) * _level
    }

    private fun calculateActionPoints(): Int {
        val baseAp = when (rolClass) {
            RolClass.WIZARD,  RolClass.WARLOCK -> 6
            RolClass.CLERIC, RolClass.DRUID, RolClass.BARD -> 5
            RolClass.EXPLORER, RolClass.ROGUE -> 4
            RolClass.WARRIOR, RolClass.PALADIN, RolClass.BARBARIAN -> 3
            else -> 3
        }
        val wisdomBonus = (wisdom - 10) / 2
        val intelligenceBonus = (intelligence - 10) / 2
        return (baseAp + wisdomBonus + intelligenceBonus).coerceAtLeast(1) * _level
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

            hp = this.hp,
            currentHp = this.currentHp,
            ap = this.ap,
            currentAp = this.currentAp,

            imgUrl = null,
            gameSessionId = this.gameSessionId,
            userId = this.userId,
            roleClass = this.rolClass.name,
            skills = emptyList()
        )
    }
}


