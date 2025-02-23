package com.unir.sheet.domain.usecase.item

data class ItemUseCases (
    val fetchItems: FetchItemsUseCase,
    val getItemsByCharacterId: GetItemsByCharacterId,
    val sellItem: SellItemUseCase,
    val destroyItem: DestroyItemUseCase,
    val addItemToCharacter: AddItemToCharacterUseCase
)