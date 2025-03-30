package com.unir.core.navigation

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
    object AdventureMainScreen: ScreensRoutes("AdventureMainScreen")
    object TemplateAdventureScreen: ScreensRoutes("TemplateAdventureScreen")

}