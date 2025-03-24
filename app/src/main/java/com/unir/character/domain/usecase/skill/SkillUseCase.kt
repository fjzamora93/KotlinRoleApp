package com.unir.character.domain.usecase.skill

import javax.inject.Inject

data class SkillUseCases @Inject constructor(
    val saveSkills: SaveSkillsUseCase,
    val getSkillsFromCharacter: GetSkillsFromCharacterUseCase,
    val validateSkillValue: ValidateSkillValue,
    val updateSkills: UpdateSkills,
    val generateSkillValues: GenerateSkillValues,
    val fetchSkills: FetchSkillsUseCase,
    val getSkills: GetSkillsUseCase
)