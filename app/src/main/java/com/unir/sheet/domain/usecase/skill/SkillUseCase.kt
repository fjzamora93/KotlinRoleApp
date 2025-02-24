package com.unir.sheet.domain.usecase.skill

import javax.inject.Inject

data class SkillUseCases @Inject constructor(
    val getAllSkills: GetAllSkillsUseCase,
    val addDefaultSkills: AddDefaultSkills,
    val deleteSkillFromCharacter: DeleteSkillFromCharacterUseCase,
    val getSkillsFromCharacter: GetSkillsFromCharacterUseCase
)