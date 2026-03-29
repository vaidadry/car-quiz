package com.dryzaite.carquiz.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Style
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import carquiz.composeapp.generated.resources.*
import com.dryzaite.carquiz.core.ui.component.BottomNav
import com.dryzaite.carquiz.core.ui.component.BottomNavTab
import com.dryzaite.carquiz.feature.home.CongratsScreen
import com.dryzaite.carquiz.feature.home.HomeMode
import com.dryzaite.carquiz.feature.home.HomeViewModel
import com.dryzaite.carquiz.feature.home.LogoQuizScreen
import com.dryzaite.carquiz.feature.home.WelcomeScreen
import com.dryzaite.carquiz.feature.learn.FlashcardDeckScreen
import com.dryzaite.carquiz.feature.learn.LearnViewModel
import com.dryzaite.carquiz.feature.stats.StatsScreen
import com.dryzaite.carquiz.feature.stats.StatsViewModel
import com.dryzaite.carquiz.core.ui.theme.AppGradientBottom
import com.dryzaite.carquiz.core.ui.theme.AppGradientMid
import com.dryzaite.carquiz.core.ui.theme.AppGradientTop
import org.koin.compose.viewmodel.koinViewModel

enum class MainTab {
    HOME,
    LEARN,
    STATS
}

@Composable
fun CarQuizApp(
    appViewModel: AppViewModel = koinViewModel(),
    homeViewModel: HomeViewModel = koinViewModel(),
    learnViewModel: LearnViewModel = koinViewModel(),
    statsViewModel: StatsViewModel = koinViewModel()
) {
    val appState by appViewModel.uiState.collectAsState()
    val homeState by homeViewModel.uiState.collectAsState()
    val learnState by learnViewModel.uiState.collectAsState()
    val statsState by statsViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        AppGradientTop,
                        AppGradientMid,
                        AppGradientBottom
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                when (appState.selectedTab) {
                    MainTab.HOME -> {
                        when (homeState.mode) {
                            HomeMode.WELCOME -> WelcomeScreen(
                                funFact = homeState.funFact,
                                onStart = homeViewModel::startQuiz
                            )
                            HomeMode.CONGRATS -> CongratsScreen(
                                score = homeState.lastScore,
                                total = homeState.lastTotal,
                                onPlayAgain = homeViewModel::playAgain,
                                onHome = homeViewModel::goHome
                            )
                            HomeMode.QUIZ -> {
                                val question = homeState.currentQuestion
                                if (question != null) {
                                    LogoQuizScreen(
                                        question = question,
                                        selectedAnswerId = homeState.selectedAnswerId,
                                        onReplayPrompt = homeViewModel::replayPrompt,
                                        onAnswerSelected = homeViewModel::selectAnswer
                                    )
                                }
                            }
                        }
                    }

                    MainTab.LEARN -> FlashcardDeckScreen(
                        brands = learnState.brands,
                        currentIndex = learnState.currentIndex,
                        onSwipedRight = learnViewModel::onSwipedRight,
                        onSwipedLeft = learnViewModel::onSwipedLeft
                    )

                    MainTab.STATS -> StatsScreen(stats = statsState.stats)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            BottomNav(
                tabs = listOf(
                    BottomNavTab(
                        icon = Icons.Filled.Home,
                        label = stringResource(Res.string.nav_home),
                        isSelected = appState.selectedTab == MainTab.HOME,
                        onClick = {
                            val wasOnHome = appState.selectedTab == MainTab.HOME
                            val isDoubleTap = appViewModel.onHomeTabSelected()
                            if (!wasOnHome || isDoubleTap) {
                                homeViewModel.goHome()
                            }
                        }
                    ),
                    BottomNavTab(
                        icon = Icons.Filled.Style,
                        label = stringResource(Res.string.nav_learn),
                        isSelected = appState.selectedTab == MainTab.LEARN,
                        onClick = {
                            homeViewModel.goHome()
                            appViewModel.selectTab(MainTab.LEARN)
                        }
                    ),
                    BottomNavTab(
                        icon = Icons.Filled.BarChart,
                        label = stringResource(Res.string.nav_stats),
                        isSelected = appState.selectedTab == MainTab.STATS,
                        onClick = {
                            homeViewModel.goHome()
                            appViewModel.selectTab(MainTab.STATS)
                        }
                    )
                )
            )
        }
    }
}
