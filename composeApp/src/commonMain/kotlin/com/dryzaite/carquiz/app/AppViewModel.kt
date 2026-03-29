package com.dryzaite.carquiz.app

import androidx.lifecycle.ViewModel
import kotlin.time.TimeMark
import kotlin.time.TimeSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AppUiState(
    val selectedTab: MainTab = MainTab.HOME
)

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var lastHomeTapMark: TimeMark? = null

    fun onHomeTabSelected(): Boolean {
        val wasAlreadySelected = _uiState.value.selectedTab == MainTab.HOME
        val isDoubleTap = wasAlreadySelected && lastHomeTapMark?.elapsedNow()?.inWholeMilliseconds?.let { it < 400L } == true
        lastHomeTapMark = TimeSource.Monotonic.markNow()
        _uiState.update { it.copy(selectedTab = MainTab.HOME) }
        return isDoubleTap
    }

    fun selectTab(tab: MainTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
