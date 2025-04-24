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



    // TEST Y TEMPLATES
    object FontTemplateScreen : ScreensRoutes("FontTemplateScreen")


    // ITEMS
    object InventoryScreen: ScreensRoutes("InventoryScreen")
    object ItemDetailScreen : ScreensRoutes("ItemDetailScreen/{itemId}") {
        fun createRoute(itemId: Int) = "ItemDetailScreen/$itemId"
    }


    // AUTH
    object LoginScreen: ScreensRoutes("LoginScreen")
    object UserProfileScreen: ScreensRoutes("UserProfileScreen")


    // ADVENTURE
    object AdventureMainScreen: ScreensRoutes("AdventureMainScreen")
    object AdventureListScreen: ScreensRoutes("AdventureListScreen")
    object TemplateAdventureScreen: ScreensRoutes("TemplateAdventureScreen")

    // GAME SESSION
    object GameSessionScreen: ScreensRoutes("GameSessionScreen")


    // HOME
    object HomeScreen: ScreensRoutes("HomeScreen")

}