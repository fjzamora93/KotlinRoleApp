package com.unir.core.di
import com.unir.auth.domain.repository.AuthRepository
import com.unir.auth.domain.repository.UserRepository
import com.unir.auth.domain.usecase.auth.AuthUseCases
import com.unir.auth.domain.usecase.auth.PostAutologinUseCase
import com.unir.auth.domain.usecase.auth.PostLoginUseCase
import com.unir.auth.domain.usecase.auth.PostLogoutUseCase
import com.unir.auth.domain.usecase.auth.PostSignupUseCase
import com.unir.auth.domain.usecase.user.DeleteUserUseCase
import com.unir.auth.domain.usecase.user.GetUserUseCase
import com.unir.auth.domain.usecase.user.UpdateUserUseCase
import com.unir.auth.domain.usecase.user.UserUseCase
import com.unir.character.domain.repository.CharacterRepository
import com.unir.character.domain.repository.ItemRepository
import com.unir.character.domain.repository.SkillRepository
import com.unir.character.domain.repository.SpellRepository
import com.unir.character.domain.usecase.character.CharacterUseCases
import com.unir.character.domain.usecase.character.CreateCharacterUseCase
import com.unir.character.domain.usecase.character.DeleteCharacterUseCase
import com.unir.character.domain.usecase.character.GetActiveCharacterIdUseCase
import com.unir.character.domain.usecase.character.GetCharacterByIdUseCase
import com.unir.character.domain.usecase.character.GetCharactersByUserUseCase
import com.unir.character.domain.usecase.character.SaveCharacterUseCase
import com.unir.character.domain.usecase.character.UpdateCharacterUseCase
import com.unir.character.domain.usecase.item.DestroyItemUseCase
import com.unir.character.domain.usecase.item.FetchTemplateItemsUseCase
import com.unir.character.domain.usecase.item.GetItemsByCharacter
import com.unir.character.domain.usecase.item.GetItemsBySessionUseCase
import com.unir.character.domain.usecase.item.ItemUseCases
import com.unir.character.domain.usecase.item.SellItemUseCase
import com.unir.character.domain.usecase.item.AddItemToCharacterUseCase
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
            createCharacter = CreateCharacterUseCase(characterRepository, skillUseCase, userUseCase),
            getActiveCharacter = GetActiveCharacterIdUseCase(characterRepository)
        )
    }

    @Provides
    @Singleton
    fun provideItemUseCases(
        itemRepository: ItemRepository,
        characterRepository: CharacterRepository,
        characterUseCase: CharacterUseCases
    ): ItemUseCases {
        return ItemUseCases(
            fetchTemplateItems = FetchTemplateItemsUseCase(itemRepository),
            getItemsByCharacter = GetItemsByCharacter(itemRepository, characterUseCase),
            getItemsBySession = GetItemsBySessionUseCase(itemRepository, characterUseCase),
            sellItem = SellItemUseCase(characterRepository, itemRepository),
            destroyItem = DestroyItemUseCase(itemRepository),
            upsertItemToCharacter = AddItemToCharacterUseCase(characterUseCase, itemRepository)
        )
    }

    // !! Las habilidades ya se están inyectando dentro del CHaracter, evitar dependencias circualares.
    @Provides
    @Singleton
    fun provideSkillUseCases(
        repository: SkillRepository,
    ): SkillUseCases {
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
    fun provideSpellUseCases(
        spellRepository: SpellRepository,
        characterUseCase: CharacterUseCases
    ): SpellUseCases {
        return SpellUseCases(
            getAllSpells = GetAllSpellsUseCase(spellRepository),
            getSpellsByLevelAndRoleClass = GetSpellsByLevelAndRoleClassUseCase(spellRepository)
        )
    }


    @Provides
    @Singleton
    fun provideAuthUseCases(repository: AuthRepository): AuthUseCases {
        return AuthUseCases(
            postLogin = PostLoginUseCase(repository),
            postSignup = PostSignupUseCase(repository),
            postLogout = PostLogoutUseCase(repository),
            postAutologin = PostAutologinUseCase(repository)
        )
    }



    @Provides
    @Singleton
    fun provideUserUseCases(repository: UserRepository): UserUseCase {
        return UserUseCase(
            getUser = GetUserUseCase(repository),
            updateUser = UpdateUserUseCase(repository),
            deleteUser = DeleteUserUseCase(repository)
        )
    }
}