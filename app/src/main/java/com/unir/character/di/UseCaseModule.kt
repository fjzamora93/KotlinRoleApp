package com.unir.character.di
import com.unir.auth.domain.usecase.user.UserUseCase
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.ItemRepository
import com.unir.character.domain.repository.SkillRepository
import com.unir.character.domain.repository.SpellRepository
import com.unir.character.domain.usecase.character.CharacterUseCases
import com.unir.character.domain.usecase.character.CreateCharacterUseCase
import com.unir.character.domain.usecase.character.DeleteCharacterUseCase
import com.unir.character.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.character.domain.usecase.character.GetCharactersByUserUseCase
import com.unir.character.domain.usecase.character.SaveCharacterUseCase
import com.unir.character.domain.usecase.character.UpdateCharacterUseCase
import com.unir.character.domain.usecase.item.DestroyItemUseCase
import com.unir.character.domain.usecase.item.FetchTemplateItemsUseCase
import com.unir.character.domain.usecase.item.GetItemsByCharacterId
import com.unir.character.domain.usecase.item.GetItemsBySessionUseCase
import com.unir.character.domain.usecase.item.ItemUseCases
import com.unir.character.domain.usecase.item.SellItemUseCase
import com.unir.character.domain.usecase.item.UpsertItemToCharacter
import com.unir.character.domain.usecase.skill.FetchSkillsUseCase
import com.unir.character.domain.usecase.skill.GenerateSkillValues
import com.unir.character.domain.usecase.skill.SaveSkillsUseCase

import com.unir.character.domain.usecase.skill.GetSkillsFromCharacterUseCase
import com.unir.character.domain.usecase.skill.GetSkillsUseCase
import com.unir.character.domain.usecase.skill.SkillUseCases
import com.unir.character.domain.usecase.skill.UpdateSkills
import com.unir.character.domain.usecase.skill.ValidateSkillValue
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
        createCharacterUseCase: CreateCharacterUseCase,
        updateCharacterUseCase: UpdateCharacterUseCase,
        skillUseCase: SkillUseCases,
        userUseCase : UserUseCase
    ): CharacterUseCases {
        return CharacterUseCases(
            getCharactersByUserId = GetCharactersByUserUseCase(characterRepository, userUseCase),
            getCharacterById = GetCharacterByIdUseCase(characterRepository),
            saveCharacter = SaveCharacterUseCase(createCharacterUseCase, updateCharacterUseCase),
            deleteCharacter = DeleteCharacterUseCase(characterRepository),
            updateCharacter = UpdateCharacterUseCase(characterRepository),
            createCharacter = CreateCharacterUseCase(characterRepository, skillUseCase, userUseCase)
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
    fun provideSkillUseCases(repository: SkillRepository): SkillUseCases {
        return SkillUseCases(
            saveSkills = SaveSkillsUseCase(repository),
            getSkillsFromCharacter = GetSkillsFromCharacterUseCase(repository),
            validateSkillValue = ValidateSkillValue(),
            updateSkills = UpdateSkills(repository),
            generateSkillValues = GenerateSkillValues(repository),
            fetchSkills = FetchSkillsUseCase(repository),
            getSkills = GetSkillsUseCase(repository)
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