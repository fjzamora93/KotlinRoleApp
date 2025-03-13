package com.unir.character.domain.usecase.item

data class ItemUseCases (
    val upsertItemToCharacter: UpsertItemToCharacter,

    val fetchTemplateItems: FetchTemplateItemsUseCase,
    val getItemsBySession: GetItemsBySessionUseCase,
    val getItemsByCharacterId: GetItemsByCharacterId,
    val sellItem: SellItemUseCase,
    val destroyItem: DestroyItemUseCase,
)