package com.unir.sheet.data.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.sheet.data.remote.model.ApiCharacterRequest

@Entity(tableName = "character_entity_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,

    // DATOS DE USUARIO Y SESIÓN
    @ColumnInfo(name = "userId")
    val userId: Int? = 0,
    var gameSessionId: Int? = null,

    // Datos del personaje
    var name: String = "",
    var description: String = "",
    var rolClass: RolClass = RolClass.WARRIOR,
    var gender: Gender = Gender.MALE,
    var race: Race = Race.HUMAN,
    var size: Int = 11,
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

    fun toApiRequest(): ApiCharacterRequest {
        return ApiCharacterRequest(
            id = this.id ?: 0,
            name = this.name,
            description = this.description,
            race = this.race.name,
            gender = this.gender.name,
            size = this.size,
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
        )
    }



    fun completeCharacter(){
        println("Procedemos a completar la ficha del personaje...")
        calculateStatsBasedOnClass()
        applyRaceBonuses()
        calculateHp()
    }



    fun applyRaceBonuses() {
        when (this.race) {
            Race.HUMAN -> {
                // Los Humanos no tienen bonificaciones ni penalizaciones, por lo que no es necesario modificar nada
            }
            Race.ELF -> {
                // Los Elfos tienen bonificaciones a Destreza y Sabiduría, pero penalizan en Fuerza
                this.strength -= 1
                this.dexterity += 2
                this.constitution -= 1
                this.wisdom += 1
            }
            Race.DWARF -> {
                // Los Enanos son fuertes y resistentes, pero menos ágiles
                this.strength += 2
                this.dexterity -= 1
                this.constitution += 2
                this.charisma -= 1
            }
            Race.ORC -> {
                // Los Orcos son fuertes, pero su inteligencia y sabiduría suelen ser más bajas
                this.strength += 2
                this.constitution += 1
                this.intelligence -= 2
                this.wisdom -= 1
            }
            Race.DRAGONBORN -> {
                // Los Dragones son poderosos en muchos aspectos, pero no tanto en lo social
                this.strength += 3
                this.dexterity += 1
                this.constitution += 2
                this.intelligence += 2
                this.wisdom += 1
                this.charisma -= 2
            }
            Race.TEFLIN -> {
                // Los Medianos (Halflings) suelen ser ágiles y carismáticos, pero débiles físicamente
                this.strength -= 1
                this.dexterity += 2
                this.wisdom += 1
                this.charisma += 1
            }
            Race.OTHER -> {
                // Para razas personalizadas, puedes agregar reglas adicionales o dejarlas en base
            }
        }
    }


    fun calculateStatsBasedOnClass() {
        when (this.rolClass) {
            RolClass.WIZARD, RolClass.WARLOCK, RolClass.SORCERER -> {
                intelligence = 15 // Atributo principal
                wisdom = 12       // Secundario
                strength = 8      // Débil
            }
            RolClass.BARD -> {
                charisma = 15     // Atributo principal
                dexterity = 14    // Secundario fuerte
                intelligence = 12 // Secundario bajo
                strength = 8      // Débil
            }
            RolClass.CLERIC, RolClass.DRUID -> {
                wisdom = 15       // Atributo principal
                constitution = 13 // Secundario fuerte
                strength = 12     // Secundario bajo
                dexterity = 8     // Débil
            }
            RolClass.WARRIOR, RolClass.PALADIN -> {
                strength = 15     // Atributo principal
                constitution = 14 // Secundario fuerte
                dexterity = 12    // Secundario bajo
                intelligence = 8  // Débil
            }
            RolClass.ROGUE -> {
                dexterity = 15    // Atributo principal
                intelligence = 13 // Secundario fuerte
                charisma = 12     // Secundario bajo
                strength = 8      // Débil
            }
            else -> {
                // Default: distribuidos más neutros
                strength = 11
                dexterity = 11
                constitution = 11
                intelligence = 11
                wisdom = 11
                charisma = 11
            }
        }
    }



    fun calculateHp(){
        this.hp = (this.size + this.constitution) / 2
        this.ap = this.intelligence + this.wisdom / 2
    }



}


enum class RolClass {
    NULL, WARRIOR, BARD, ROGUE, EXPLORER, CLERIC, PALADIN, SORCERER, WIZARD, DRUID, MONK, WARLOCK, BARBARIAN;

    companion object {
        fun getString(rolClass: RolClass): String {
            return when (rolClass) {
                NULL -> "None"
                WARRIOR -> "Guerrero"
                BARD -> "Bardo"
                ROGUE -> "Pícaro"
                EXPLORER -> "Explorador"
                CLERIC -> "Clérigo"
                PALADIN -> "Paladín"
                SORCERER -> "Hechicero"
                WIZARD -> "Mago"
                DRUID -> "Druida"
                MONK -> "Monje"
                WARLOCK -> "Brujo"
                BARBARIAN -> "Bárbaro"
            }
        }
    }
}

enum class Race {
    HUMAN, ELF, DWARF, TEFLIN, DRAGONBORN, ORC, OTHER;

    companion object {
        fun getString(race: Race): String {
            return when (race) {
                HUMAN -> "Humano"
                ELF -> "Elfo"
                DWARF -> "Enano"
                TEFLIN -> "Mediano"
                DRAGONBORN -> "Dragonborn"
                ORC -> "Orco"
                OTHER -> "Otro"
            }
        }
    }
}



enum class Gender{MALE, FEMALE, NEUTRAL}