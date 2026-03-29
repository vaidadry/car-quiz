package com.dryzaite.carquiz.domain

import carquiz.composeapp.generated.resources.*
import carquiz.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.StringResource
import kotlin.random.Random

class FunFactsUseCase {
    private val facts = listOf(
        Res.string.fun_fact_1,
        Res.string.fun_fact_2,
        Res.string.fun_fact_3,
        Res.string.fun_fact_4,
        Res.string.fun_fact_5,
        Res.string.fun_fact_6,
        Res.string.fun_fact_7,
        Res.string.fun_fact_8,
        Res.string.fun_fact_9,
        Res.string.fun_fact_10,
        Res.string.fun_fact_11,
        Res.string.fun_fact_12,
        Res.string.fun_fact_13,
        Res.string.fun_fact_14,
        Res.string.fun_fact_15,
        Res.string.fun_fact_16,
        Res.string.fun_fact_17,
        Res.string.fun_fact_18,
        Res.string.fun_fact_19,
        Res.string.fun_fact_20
    )

    fun randomFact(): StringResource = facts[Random.nextInt(facts.size)]
}
