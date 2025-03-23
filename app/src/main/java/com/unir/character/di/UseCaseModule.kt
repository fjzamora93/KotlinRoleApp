package com.unir.character.di
import com.unir.auth.domain.repository.AuthRepository
import com.unir.auth.domain.repository.UserRepository
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.ItemRepository
import com.unir.character.domain.repository.SkillRepository
import com.unir.character.domain.repository.SpellRepository
import com.unir.character.domain.usecase.character.CharacterUseCases
import com.unir.character.domain.usecase.character.CreateCharacterUseCase
import com.unir.character.domain.usecase.character.DeleteCharacterUseCase
import com.unir.character.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.character.domain.usecase.character.GetCharactersByUserIdUseCase
import com.unir.character.domain.usecase.character.SaveCharacterUseCase
import com.unir.character.domain.usecase.character.UpdateCharacterUseCase
import com.unir.character.domain.usecase.item.DestroyItemUseCase
import com.unir.character.domain.usecase.item.FetchTemplateItemsUseCase
import com.unir.character.domain.usecase.item.GetItemsByCharacterId
import com.unir.character.domain.usecase.item.GetItemsBySessionUseCase
import com.unir.character.domain.usecase.item.ItemUseCases
import com.unir.character.domain.usecase.item.SellItemUseCase
import com.unir.character.domain.usecase.item.UpsertItemToCharacter
import com.unir.character.domain.usecase.skill.FetchSkills
import com.unir.character.domain.usecase.skill.GenerateSkillValues
import com.unir.character.domain.usecase.skill.SaveSkills

import com.unir.character.domain.usecase.skill.GetSkillsFromCharacterUseCase
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
        userRepository: UserRepository,
        skillRepository: SkillRepository
    ): CharacterUseCases {
        return CharacterUseCases(
            getCharactersByUserId = GetCharactersByUserIdUseCase(characterRepository),
            getCharacterById = GetCharacterByIdUseCase(characterRepository),
            saveCharacter = SaveCharacterUseCase(createCharacterUseCase, updateCharacterUseCase),
            deleteCharacter = DeleteCharacterUseCase(characterRepository),
            updateCharacter = UpdateCharacterUseCase(characterRepository),
            createCharacter = CreateCharacterUseCase(skillRepository, userRepository)
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
            saveSkills = SaveSkills(repository),
            getSkillsFromCharacter = GetSkillsFromCharacterUseCase(repository),
            validateSkillValue = ValidateSkillValue(),
            updateSkills = UpdateSkills(repository),
            generateSkillValues = GenerateSkillValues(repository),
            fetchSkills = FetchSkills(repository)
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