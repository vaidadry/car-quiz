package com.dryzaite.carquiz.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dryzaite.carquiz.shared.model.BrandCatalog
import com.dryzaite.carquiz.shared.quiz.QuizEngine
import com.dryzaite.carquiz.shared.quiz.QuizQuestion
import com.dryzaite.carquiz.speech.AndroidBrandSpeaker
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class HomeMode {
    WELCOME,
    QUIZ,
    CONGRATS
}

data class HomeUiState(
    val mode: HomeMode = HomeMode.WELCOME,
    val currentQuestion: QuizQuestion? = null,
    val selectedAnswerId: String? = null,
    val funFact: String = "",
    val lastScore: Int = 0,
    val lastTotal: Int = 0
)

class HomeViewModel(
    private val brandSpeaker: AndroidBrandSpeaker,
    private val statsRepository: GameStatsRepository,
    private val funFacts: List<String>
) : ViewModel() {
    private var quizEngine = QuizEngine(BrandCatalog.allBrands, totalQuestions = 10)
    private var answerJob: Job? = null

    private val _uiState = MutableStateFlow(
        HomeUiState(funFact = funFacts.randomOrEmpty())
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun startQuiz() {
        answerJob?.cancel()
        quizEngine = QuizEngine(BrandCatalog.allBrands, totalQuestions = 10)
        val question = quizEngine.nextQuestion()
        _uiState.value = HomeUiState(
            mode = HomeMode.QUIZ,
            currentQuestion = question,
            funFact = _uiState.value.funFact
        )
        speakCurrentBrand()
    }

    fun replayPrompt() {
        speakCurrentBrand()
    }

    fun selectAnswer(answerId: String) {
        val state = _uiState.value
        val question = state.currentQuestion ?: return
        if (state.mode != HomeMode.QUIZ || state.selectedAnswerId != null) return

        quizEngine.submitAnswer(question, answerId)
        _uiState.update { it.copy(selectedAnswerId = answerId) }

        answerJob?.cancel()
        answerJob = viewModelScope.launch {
            delay(650)
            val nextQuestion = quizEngine.nextQuestion()
            if (nextQuestion == null) {
                val score = quizEngine.correctAnswers
                val total = quizEngine.answeredQuestions
                _uiState.value = HomeUiState(
                    mode = HomeMode.CONGRATS,
                    funFact = _uiState.value.funFact,
                    lastScore = score,
                    lastTotal = total
                )
                statsRepository.recordQuiz(score, total)
            } else {
                _uiState.update {
                    it.copy(
                        currentQuestion = nextQuestion,
                        selectedAnswerId = null
                    )
                }
                speakCurrentBrand()
            }
        }
    }

    fun playAgain() {
        startQuiz()
    }

    fun goHome() {
        answerJob?.cancel()
        _uiState.update {
            it.copy(
                mode = HomeMode.WELCOME,
                currentQuestion = null,
                selectedAnswerId = null,
                funFact = funFacts.randomOrEmpty()
            )
        }
    }

    override fun onCleared() {
        answerJob?.cancel()
        brandSpeaker.shutdown()
        super.onCleared()
    }

    private fun speakCurrentBrand() {
        val brandName = _uiState.value.currentQuestion?.promptBrand?.displayName ?: return
        brandSpeaker.speak(brandName)
    }

    private fun List<String>.randomOrEmpty(): String =
        if (isEmpty()) "" else this[Random.nextInt(size)]
}
