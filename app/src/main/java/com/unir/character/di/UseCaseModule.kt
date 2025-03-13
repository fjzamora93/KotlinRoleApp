package com.unir.character.di
import com.data.MyDatabase
import com.unir.character.data.dao.CharacterDao
import com.unir.character.data.dao.ItemDao
import com.unir.character.data.dao.SkillDao
import com.unir.character.data.dao.SpellDao
import com.unir.character.data.repository.SkillRepositoryImpl
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.ItemRepository
import com.unir.character.domain.repository.SpellRepository
import com.unir.character.domain.usecase.character.CharacterUseCases
import com.unir.character.domain.usecase.character.DeleteCharacterUseCase
import com.unir.character.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.character.domain.usecase.character.GetCharactersByUserIdUseCase
import com.unir.character.domain.usecase.character.UpdateCharacterUseCase
import com.unir.character.domain.usecase.item.DestroyItemUseCase
import com.unir.character.domain.usecase.item.FetchTemplateItemsUseCase
import com.unir.character.domain.usecase.item.GetItemsByCharacterId
import com.unir.character.domain.usecase.item.GetItemsBySessionUseCase
import com.unir.character.domain.usecase.item.ItemUseCases
import com.unir.character.domain.usecase.item.SellItemUseCase
import com.unir.character.domain.usecase.item.UpsertItemToCharacter
import com.unir.character.domain.usecase.skill.AddDefaultSkills
import com.unir.character.domain.usecase.skill.DeleteSkillFromCharacterUseCase
import com.unir.character.domain.usecase.skill.GetAllSkillsUseCase
import com.unir.character.domain.usecase.skill.GetSkillsFromCharacterUseCase
import com.unir.character.domain.usecase.skill.SkillUseCases
import com.unir.character.domain.usecase.spell.GetAllSpellsUseCase
import com.unir.character.domain.usecase.spell.GetSpellsByLevelAndRoleClassUseCase
import com.unir.character.domain.usecase.spell.SpellUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideCharacterUseCases(
        characterRepository: CharacterRepository,
    ): CharacterUseCases {
        return CharacterUseCases(
            getCharactersByUserId = GetCharactersByUserIdUseCase(characterRepository),
            getCharacterById = GetCharacterByIdUseCase(characterRepository),
            updateCharacter = UpdateCharacterUseCase(characterRepository),
            deleteCharacter = DeleteCharacterUseCase(characterRepository)
        )
    }

    @Provides
    @Singleton
    fun provideItemUseCases(
        itemRepository: ItemRepository,
        characterRepository: CharacterRepository
    ): ItemUseCases {
        return ItemUseCases(
            fetchTemplateItems = FetchTemplateItemsUseCase(itemRepository),
            getItemsByCharacterId = GetItemsByCharacterId(itemRepository),
            getItemsBySession = GetItemsBySessionUseCase(itemRepository),
            sellItem = SellItemUseCase(characterRepository, itemRepository),
            destroyItem = DestroyItemUseCase(itemRepository),
            upsertItemToCharacter = UpsertItemToCharacter(characterRepository, itemRepository)
        )
    }

    @Provides
    @Singleton
    fun provideSkillUseCases(repository: SkillRepositoryImpl): SkillUseCases {
        return SkillUseCases(
            getAllSkills = GetAllSkillsUseCase(repository),
            addDefaultSkills = AddDefaultSkills(repository),
            deleteSkillFromCharacter = DeleteSkillFromCharacterUseCase(repository),
            getSkillsFromCharacter = GetSkillsFromCharacterUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideSpellUseCases(spellRepository: SpellRepository): SpellUseCases {
        return SpellUseCases(
            getAllSpells = GetAllSpellsUseCase(spellRepository),
            getSpellsByLevelAndRoleClass = GetSpellsByLevelAndRoleClassUseCase(spellRepository)
        )
    }
}