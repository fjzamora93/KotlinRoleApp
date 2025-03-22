package com.unir.character.domain.usecase.skill

import javax.inject.Inject

data class SkillUseCases @Inject constructor(
    val saveSkills: SaveSkills,
    val getSkillsFromCharacter: GetSkillsFromCharacterUseCase,
    val validateSkillValue: ValidateSkillValue,
    val updateSkills: UpdateSkills,
    val generateSkillValues: GenerateSkillValues,
    val fetchSkills: FetchSkills
)