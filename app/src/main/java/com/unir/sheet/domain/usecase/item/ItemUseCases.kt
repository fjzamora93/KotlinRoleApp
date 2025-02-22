package com.unir.sheet.domain.usecase.item

data class ItemUseCases (
    val fetchItems: FetchItemsUseCase,
    val getItemsByCharacterId: GetItemsByCharacterId,
    val addItemToCharacter: AddItemToCharacterUseCase,
    val sellItem: SellItemUseCase,
    val destroyItem: DestroyItemUseCase,
)