package com.unir.character.data.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.unir.character.data.model.remote.SkillDTO

@Entity(tableName = "SkillTable")
data class Skill(
    @PrimaryKey(autoGenerate = true) val id:Int,
    var name: String = "",
    var description: String = "",
    var tag: String = "",
){
    fun toApiSkill(): SkillDTO {
        return SkillDTO(
            id = this.id,
            name = this.name,
            description = this.description,
            tag = this.tag
        )
    }
}

// NO ES NECESARIO, LAS SKILLS SE CARGAN DESDE LA API
object SkillTags {
    const val STRENGTH = "STR"
    const val DEXTERITY = "DEX"
    const val INTELLIGENCE = "INT"
    const val CHARISMA = "CHA"
    const val COMBAT = "COMBAT"
}

val initialSkills = listOf(
    // FÍSICAS (basadas en Fuerza) - STR
    Skill(1, "Supervivencia (FUE)", "Habilidad para sobrevivir en entornos hostiles y condiciones extremas.", SkillTags.STRENGTH),
    Skill(2, "Artesanía (FUE)", "Capacidad para crear objetos y herramientas con fuerza y precisión.", SkillTags.STRENGTH),
    Skill(3, "Atletismo (FUE)", "Habilidad para realizar actividades físicas que requieren fuerza y resistencia.", SkillTags.STRENGTH),
    Skill(4, "Nadar (FUE)", "Capacidad para moverse en el agua con fuerza y eficacia.", SkillTags.STRENGTH),
    Skill(5, "Intimidar (FUE)", "Habilidad para asustar o coaccionar a otros mediante la fuerza física.", SkillTags.STRENGTH),

    // HABILIDAD (basadas en destreza) - DEX
    Skill(6, "Cabalgar (DES)", "Habilidad para montar y controlar animales de montura con destreza.", SkillTags.DEXTERITY),
    Skill(7, "Pesca y caza (DES)", "Técnicas para capturar peces y animales salvajes con precisión.", SkillTags.DEXTERITY),
    Skill(8, "Dedos ágiles (DES)", "Destreza manual para realizar tareas delicadas y precisas.", SkillTags.DEXTERITY),
    Skill(9, "Esquivar (DES)", "Habilidad para evitar ataques o peligros con movimientos ágiles.", SkillTags.DEXTERITY),
    Skill(10, "Sigilo (DES)", "Capacidad para moverse sin ser detectado, utilizando la destreza.", SkillTags.DEXTERITY),

    // MENTALES (basadas en inteligencia) - INT
    Skill(11, "Alerta (INT)", "Capacidad para detectar peligros o cambios en el entorno con agudeza mental.", SkillTags.INTELLIGENCE),
    Skill(12, "Naturaleza (INT)", "Conocimiento sobre animales o plantas y cómo interactuar con ellos de manera inteligente.", SkillTags.INTELLIGENCE),
    Skill(13, "Curación (INT)", "Técnicas para tratar heridas y enfermedades utilizando el conocimiento.", SkillTags.INTELLIGENCE),
    Skill(14, "Percepción (INT)", "Habilidad para notar detalles y anomalías con agudeza mental.", SkillTags.INTELLIGENCE),
    Skill(15, "Mitos y leyendas (INT)", "Conocimiento sobre historias, mitos y folklore de diferentes culturas.", SkillTags.INTELLIGENCE),

    // SOCIALES (basadas en carisma) - CHA
    Skill(16, "Engañar (CAR)", "Habilidad para mentir o manipular a otros con carisma y persuasión.", SkillTags.CHARISMA),
    Skill(17, "Interpretar (CAR)", "Capacidad para actuar, cantar o expresarse de manera convincente.", SkillTags.CHARISMA),
    Skill(18, "Persuadir (CAR)", "Habilidad para convencer a otros con argumentos carismáticos.", SkillTags.CHARISMA),
    Skill(19, "Negociar (CAR)", "Técnicas para llegar a acuerdos beneficiosos mediante el carisma.", SkillTags.CHARISMA),
    Skill(20, "Seducir (CAR)", "Habilidad para atraer o influir en otros mediante el encanto y la persuasión.", SkillTags.CHARISMA),

    // COMPETENCIA EN ARMAS - COMBAT
    Skill(21, "A distancia (DES)", "Habilidad para usar armas a distancia como arcos, ballestas, cerbatanas u hondas.", SkillTags.COMBAT),
    Skill(22, "Armas blancas (DES)", "Habilidad para usar espadas cortas, como dagas, cuchillos o puñales.", SkillTags.COMBAT),
    Skill(23, "Armas de asta (FUE)", "Habilidad para manejar lanzas, varas, alabardas o guadañas.", SkillTags.COMBAT),
    Skill(24, "Armas pesadas (FUE)", "Habilidad para manejar armas pesadas a dos manos como mazas, mandobles, martillos u hachas.", SkillTags.COMBAT),
    Skill(25, "Espadas (FUE)", "Manejo de todo tipo de espadas para el combate cuerpo a cuerpo.", SkillTags.COMBAT)
)


