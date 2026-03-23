package com.dryzaite.carquiz.ui.quiz

import androidx.lifecycle.ViewModel
import com.dryzaite.carquiz.domain.usecase.GenerateQuizQuestionUseCase
import com.dryzaite.carquiz.domain.usecase.SubmitAnswerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel(
    private val generateQuizQuestionUseCase: GenerateQuizQuestionUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        loadQuestion()
    }

    fun onAnswerSelected(brandId: String) {
        val question = _uiState.value.question ?: return
        val isCorrect = submitAnswerUseCase.isAnswerCorrect(question, brandId)
        _uiState.value = _uiState.value.copy(
            feedbackMessage = if (isCorrect) {
                "Great job! 🎉"
            } else {
                "Nice try! Let's do one more 🚘"
            },
        )
    }

    fun nextQuestion() {
        loadQuestion()
    }

    private fun loadQuestion() {
        _uiState.value = QuizUiState(
            question = generateQuizQuestionUseCase(),
            feedbackMessage = null,
        )
    }
}