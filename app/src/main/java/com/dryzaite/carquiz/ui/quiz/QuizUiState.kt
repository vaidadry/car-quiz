package com.dryzaite.carquiz.ui.quiz

import com.dryzaite.carquiz.domain.model.QuizQuestion

data class QuizUiState(
    val question: QuizQuestion? = null,
    val feedbackMessage: String? = null,
)