package com.dryzaite.carquiz.ui

import androidx.lifecycle.ViewModel
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

    private var lastHomeTapMs: Long = 0L

    fun onHomeTabSelected(nowMs: Long): Boolean {
        val wasAlreadySelected = _uiState.value.selectedTab == MainTab.HOME
        val isDoubleTap = wasAlreadySelected && nowMs - lastHomeTapMs < 400L
        lastHomeTapMs = nowMs
        _uiState.update { it.copy(selectedTab = MainTab.HOME) }
        return isDoubleTap
    }

    fun selectTab(tab: MainTab) {
        _uiState.update { it.copy(selectedTab = tab) }
    }
}
