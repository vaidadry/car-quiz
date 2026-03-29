package com.dryzaite.carquiz.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dryzaite.carquiz.domain.FunFactsUseCase
import com.dryzaite.carquiz.core.model.BrandCatalog
import com.dryzaite.carquiz.core.quiz.QuizEngine
import com.dryzaite.carquiz.core.quiz.QuizQuestion
import com.dryzaite.carquiz.speech.BrandSpeaker
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import org.jetbrains.compose.resources.StringResource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class HomeMode {
    WELCOME,
    QUIZ,
    CONGRATS
}

data class HomeUiState(
    val mode: HomeMode = HomeMode.WELCOME,
    val currentQuestion: QuizQuestion? = null,
    val selectedAnswerId: String? = null,
    val funFact: StringResource? = null,
    val lastScore: Int = 0,
    val lastTotal: Int = 0
)

class HomeViewModel(
    private val brandSpeaker: BrandSpeaker,
    private val statsRepository: GameStatsRepository,
    private val funFactsUseCase: FunFactsUseCase
) : ViewModel() {
    private var quizEngine = QuizEngine(BrandCatalog.allBrands, totalQuestions = 10)
    private var answerJob: Job? = null

    private val _uiState = MutableStateFlow(
        HomeUiState(funFact = funFactsUseCase.randomFact())
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
                funFact = funFactsUseCase.randomFact()
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
}
