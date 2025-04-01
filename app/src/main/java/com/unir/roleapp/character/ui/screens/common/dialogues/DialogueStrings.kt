package com.roleapp.character.ui.screens.common.dialogues

val dialogTexts = mapOf(
    CharacterDialog.Armour to """
        Utiliza la armadura para reducir el daño recibido durante un impacto o ataque. 
        Por ejemplo, si un enemigo lanza un ataque que inflige 7 puntos de daño y cuentas con 3 puntos de armadura, 
        se restarán esos 3 puntos de armadura al daño, lo que resultará en solo 4 puntos de daño recibido.
        
        Cada tipo de armadura cuenta con sus ventajas e inconvenientes. Así, las armaduras más pesadas suelen limitar la movilidad y ciertas acciones (consultar en el inventario).
    """.trimIndent(),

    CharacterDialog.Stats to """
        las tiradas de éxito o fracaso no se realizan sobre los atributos directamente, sino sobre las habilidades del personaje. 
        Las habilidades son las que determinan si una acción específica tiene éxito o no, 
        y los dados se lanzan en función de ellas.
        
    
        También puedes decidir repetir una tirada de un atributo competente aunque todas las habilidades que involucren a dicho atributo tirarán con desventaja hasta que 
        el personaje se recupere.
        
        Por ejemplo, un personaje podría estar agotado debido a una tirada fallida de Fuerza donde repitió la tirada de dados, 
        lo que afectaría su capacidad para realizar acciones físicas hasta que se recupere (tirará con desventaja, es decir, dos d20 y se quedará con el resultado más bajo). 
        De manera similar, las consecuencias de una tirada fallida pueden incluir efectos negativos temporales en otros atributos, como:
        
        - Fuerza: pasa a estar agotado.
        - Constitución: pasa a estar enfermo.
        - Destreza: pasa a estar torpe.
        - Inteligencia: pasa a estar aturdido.
        - Sabiduría: pasa a estar asustado.
        - Carisma: pasa a estar desmotivado.
        
    """.trimIndent(),


    CharacterDialog.Skills to """
        Las habilidades determinan el éxito / fracaso de cada tirada y pueden mejorarse al subir de nivel.
        
        Al contrario que otros sistemas de juego, se considera éxito cuando el resultado de la tirada es menor o igual a la puntuación por dicha habilidad,
        y se consdiera fracaso si se supera la puntuación de la habilidad (no hay modificadores). En este sentido, un 1 es un golpe crítico, y un 20 se considera pifia.
        
        Por ejemplo, si un personaje intenta persuadir a un NPC para que haga determinada acción, y dicho personaje tiene 15 puntos en engaño, 13 en persuasión y 9 en seducción,
        idealmente el jugador propondrá realizar una tirada por "Engaño" (ya que es su habilidad más competente), y tendrá éxito si saca 15 o menos.
        
        Sin embargo, el director del juego puede poner límites a las habilidades que se pueden usar en cada circunstancia.
        Por ejemplo, podría alegar que cierto NPC es imposible se engañar o no se deja seducir, pero que sin embargo sí está abierto a negociar (entonces se forzaría una tirada de Negociación).
        
        """.trimIndent(),

    CharacterDialog.Initiative to "La iniciativa representa la rapidez con la que el personaje actúa en combate.",
    CharacterDialog.Inventory to "Aquí puedes gestionar el inventario, los hechizos y otros recursos del personaje."


)