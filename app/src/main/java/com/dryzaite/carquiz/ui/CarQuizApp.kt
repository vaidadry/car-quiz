package com.dryzaite.carquiz.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.dryzaite.carquiz.R
import com.dryzaite.carquiz.shared.model.BrandCatalog
import com.dryzaite.carquiz.ui.foundation.BottomNav
import com.dryzaite.carquiz.ui.foundation.BottomNavTab
import com.dryzaite.carquiz.ui.screen.home.CongratsScreen
import com.dryzaite.carquiz.ui.screen.home.LogoQuizScreen
import com.dryzaite.carquiz.ui.screen.home.WelcomeScreen
import com.dryzaite.carquiz.ui.screen.learn.FlashcardDeckScreen
import com.dryzaite.carquiz.ui.screen.stats.StatsScreen
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import com.dryzaite.carquiz.stats.data.PersistedStats
import com.dryzaite.carquiz.ui.theme.AppGradientBottom
import com.dryzaite.carquiz.ui.theme.AppGradientMid
import com.dryzaite.carquiz.ui.theme.AppGradientTop
import kotlinx.coroutines.launch

enum class MainTab {
    HOME,
    LEARN,
    STATS
}

@Composable
fun CarQuizApp(
    statsRepository: GameStatsRepository,
    onSpeakBrand: (String) -> Unit,
    onPositiveSwipe: () -> Unit
) {
    var tab by remember { mutableStateOf(MainTab.HOME) }
    var showWelcome by remember { mutableStateOf(true) }
    var showCongrats by remember { mutableStateOf(false) }
    var quizSessionId by remember { mutableIntStateOf(0) }
    var lastHomeTapMs by remember { mutableLongStateOf(0L) }
    var lastScore by remember { mutableIntStateOf(0) }
    var lastTotal by remember { mutableIntStateOf(0) }
    var rightSwipes by remember { mutableIntStateOf(0) }
    var totalSwipes by remember { mutableIntStateOf(0) }

    val scope = rememberCoroutineScope()
    val persistedStats by statsRepository.stats.collectAsState(initial = PersistedStats())

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

            Box(modifier = Modifier.weight(1f)) {
                when (tab) {
                    MainTab.HOME -> {
                        when {
                            showWelcome -> WelcomeScreen(onStart = {
                                showWelcome = false
                                showCongrats = false
                                quizSessionId += 1
                            })

                            showCongrats -> CongratsScreen(
                                score = lastScore,
                                total = lastTotal,
                                onPlayAgain = {
                                    showCongrats = false
                                    showWelcome = false
                                    quizSessionId += 1
                                },
                                onHome = {
                                    showCongrats = false
                                    showWelcome = true
                                }
                            )

                            else -> LogoQuizScreen(
                                quizSessionId = quizSessionId,
                                onSpeakBrand = onSpeakBrand,
                                onQuizComplete = { score, total ->
                                    lastScore = score
                                    lastTotal = total
                                    showCongrats = true
                                    scope.launch { statsRepository.recordQuiz(score, total) }
                                }
                            )
                        }
                    }

                    MainTab.LEARN -> FlashcardDeckScreen(
                        brands = BrandCatalog.allBrands.shuffled(),
                        onSwipedRight = {
                            val newRightSwipes = rightSwipes + 1
                            val newTotalSwipes = totalSwipes + 1
                            rightSwipes = newRightSwipes
                            totalSwipes = newTotalSwipes
                            onPositiveSwipe()

                            scope.launch {
                                statsRepository.recordFlashcards(
                                    rightGuessed = newRightSwipes,
                                    totalSwipes = newTotalSwipes
                                )
                            }
                        },
                        onSwipedLeft = {
                            val newTotalSwipes = totalSwipes + 1
                            totalSwipes = newTotalSwipes

                            scope.launch {
                                statsRepository.recordFlashcards(
                                    rightGuessed = rightSwipes,
                                    totalSwipes = newTotalSwipes
                                )
                            }
                        }
                    )

                    MainTab.STATS -> StatsScreen(stats = persistedStats)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            BottomNav(
                tabs = listOf(
                    BottomNavTab(
                        icon = Icons.Filled.Home,
                        label = stringResource(R.string.nav_home),
                        isSelected = tab == MainTab.HOME,
                        onClick = {
                            val clicked = MainTab.HOME
                            val now = System.currentTimeMillis()
                            val isDoubleTap = tab == MainTab.HOME && now - lastHomeTapMs < 400L
                            if (isDoubleTap) {
                                showWelcome = true
                                showCongrats = false
                                quizSessionId += 1
                            } else if (tab != MainTab.HOME) {
                                showWelcome = true
                                showCongrats = false
                            }
                            lastHomeTapMs = now
                            tab = clicked
                        }
                    ),
                    BottomNavTab(
                        icon = Icons.Filled.Style,
                        label = stringResource(R.string.nav_learn),
                        isSelected = tab == MainTab.LEARN,
                        onClick = {
                            showCongrats = false
                            tab = MainTab.LEARN
                        }
                    ),
                    BottomNavTab(
                        icon = Icons.Filled.BarChart,
                        label = stringResource(R.string.nav_stats),
                        isSelected = tab == MainTab.STATS,
                        onClick = {
                            showCongrats = false
                            tab = MainTab.STATS
                        }
                    )
                )
            )
        }
    }
}
