package com.unir.sheet.domain.usecase.item

data class ItemUseCases (
    val fetchTemplateItems: FetchTemplateItemsUseCase,
    val getItemsBySession: GetItemsBySessionUseCase,
    val getItemsByCharacterId: GetItemsByCharacterId,
    val sellItem: SellItemUseCase,
    val destroyItem: DestroyItemUseCase,
    val addItemToCharacter: AddItemToCharacterUseCase
)