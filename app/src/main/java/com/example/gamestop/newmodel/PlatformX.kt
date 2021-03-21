package com.example.gamestop.newmodel

import java.io.Serializable

data class PlatformX(
    val platform: PlatformXX,
    val released_at: String,
    val requirements_en: RequirementsEn,
    val requirements_ru: Any
): Serializable