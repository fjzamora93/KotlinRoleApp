package com.roleapp.core.navigation

sealed class ScreensRoutes(val route: String) {
    object CharacterListScreen : ScreensRoutes("CharacterListScreen")
    object CharacterSpellScreen : ScreensRoutes("CharacterSpellScreen")
    object CharacterDetailScreen : ScreensRoutes("CharacterDetailScreen/{characterId}") {
        fun createRoute(characterId: Long) = "CharacterDetailScreen/$characterId"
    }
    object CharacterEditorScreen: ScreensRoutes("CharacterEditorScreen/{characterId}"){
        fun createRoute(characterId: Long) = "CharacterEditorScreen/$characterId"
    }


    object SkillListScreen : ScreensRoutes("SkillListScreen")

    // TEST Y TEMPLATES
    object FontTemplateScreen : ScreensRoutes("FontTemplateScreen")


    // ITEMS
    object ItemListScreen: ScreensRoutes("ItemListScreen")
    object InventoryScreen: ScreensRoutes("InventoryScreen")
    object ItemDetailScreen : ScreensRoutes("ItemDetailScreen/{itemId}") {
        fun createRoute(itemId: Int) = "ItemDetailScreen/$itemId"
    }


    // AUTH
    object LoginScreen: ScreensRoutes("LoginScreen")
    object UserProfileScreen: ScreensRoutes("UserProfileScreen")


    // ADVENTURE
<<<<<<< Updated upstream
    object AdventureMainScreen: ScreensRoutes("AdventureMainScreen")
=======
    //object AdventureMainScreen : ScreensRoutes("adventure_main")
    //object AdventureMainScreen: ScreensRoutes("AdventureMainScreen")
    object AdventureMainScreen : ScreensRoutes("AdventureMainScreen")
    object AdventureListScreen: ScreensRoutes("AdventureListScreen")
>>>>>>> Stashed changes
    object TemplateAdventureScreen: ScreensRoutes("TemplateAdventureScreen")
    object CreateAdventureScreen : ScreensRoutes("CreateAdventureScreen")
    object AdventureContextScreen : ScreensRoutes("adventure_context/{adventureId}") {
        fun createRoute(adventureId: String) = "adventure_context/$adventureId"
    }

    // TEST
    object AiTestScreen  : ScreensRoutes("AiTestScreen")

}