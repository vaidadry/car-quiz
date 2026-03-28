package com.dryzaite.carquiz.ui.screen.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dryzaite.carquiz.audio.SuccessSfxPlayer
import com.dryzaite.carquiz.shared.model.BrandCatalog
import com.dryzaite.carquiz.shared.model.CarBrand
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class LearnUiState(
    val brands: List<CarBrand> = BrandCatalog.allBrands.shuffled(),
    val currentIndex: Int = 0,
    val rightSwipes: Int = 0,
    val totalSwipes: Int = 0
)

class LearnViewModel(
    private val successSfxPlayer: SuccessSfxPlayer,
    private val statsRepository: GameStatsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearnUiState())
    val uiState: StateFlow<LearnUiState> = _uiState.asStateFlow()

    fun onSwipedRight() {
        val currentState = _uiState.value
        val nextState = currentState.copy(
            currentIndex = (currentState.currentIndex + 1) % currentState.brands.size,
            rightSwipes = currentState.rightSwipes + 1,
            totalSwipes = currentState.totalSwipes + 1
        )
        _uiState.value = nextState
        successSfxPlayer.playSuccess()

        viewModelScope.launch {
            statsRepository.recordFlashcards(
                rightGuessed = nextState.rightSwipes,
                totalSwipes = nextState.totalSwipes
            )
        }
    }

    fun onSwipedLeft() {
        val currentState = _uiState.value
        val nextState = currentState.copy(
            currentIndex = (currentState.currentIndex + 1) % currentState.brands.size,
            totalSwipes = currentState.totalSwipes + 1
        )
        _uiState.value = nextState

        viewModelScope.launch {
            statsRepository.recordFlashcards(
                rightGuessed = nextState.rightSwipes,
                totalSwipes = nextState.totalSwipes
            )
        }
    }

    override fun onCleared() {
        successSfxPlayer.release()
        super.onCleared()
    }
}
