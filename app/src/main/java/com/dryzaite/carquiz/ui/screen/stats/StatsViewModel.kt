package com.dryzaite.carquiz.ui.screen.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import com.dryzaite.carquiz.stats.data.PersistedStats
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class StatsUiState(
    val stats: PersistedStats = PersistedStats()
)

class StatsViewModel(
    private val statsRepository: GameStatsRepository
) : ViewModel() {
    val uiState: StateFlow<StatsUiState> = statsRepository.stats
        .map { StatsUiState(stats = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = StatsUiState()
        )
}
