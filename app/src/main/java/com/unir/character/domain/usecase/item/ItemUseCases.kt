package com.unir.character.domain.usecase.item

data class ItemUseCases (
    val upsertItemToCharacter: AddItemToCharacterUseCase,

    val fetchTemplateItems: FetchTemplateItemsUseCase,
    val getItemsBySession: GetItemsBySessionUseCase,
    val getItemsByCharacter: GetItemsByCharacter,
    val sellItem: SellItemUseCase,
    val destroyItem: DestroyItemUseCase,
)