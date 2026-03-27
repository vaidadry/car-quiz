package com.dryzaite.carquiz.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dryzaite.carquiz.shared.model.BrandCatalog
import com.dryzaite.carquiz.shared.model.CarBrand
import com.dryzaite.carquiz.shared.quiz.QuizEngine
import com.dryzaite.carquiz.stats.data.GameStatsRepository
import com.dryzaite.carquiz.stats.data.PersistedStats
import com.dryzaite.carquiz.ui.theme.AppBorderNeutral
import com.dryzaite.carquiz.ui.theme.AppError
import com.dryzaite.carquiz.ui.theme.AppGradientBottom
import com.dryzaite.carquiz.ui.theme.AppGradientMid
import com.dryzaite.carquiz.ui.theme.AppGradientTop
import com.dryzaite.carquiz.ui.theme.AppLogoTile
import com.dryzaite.carquiz.ui.theme.AppSuccess
import com.dryzaite.carquiz.ui.theme.AppSurface
import com.dryzaite.carquiz.ui.theme.AppSurfaceSoft
import com.dryzaite.carquiz.ui.theme.AppSurfaceTint
import com.dryzaite.carquiz.ui.theme.AppTextPrimary
import com.dryzaite.carquiz.ui.theme.AppTextSecondary
import com.dryzaite.carquiz.ui.theme.BrandPrimary
import com.dryzaite.carquiz.ui.theme.BrandPrimarySoft
import com.dryzaite.carquiz.ui.theme.BrandSecondary
import com.dryzaite.carquiz.ui.theme.BrandSecondarySoft
import com.dryzaite.carquiz.ui.theme.BrandTertiary
import com.dryzaite.carquiz.ui.theme.CarQuizTheme
import com.dryzaite.carquiz.ui.theme.HomeFactCard
import com.dryzaite.carquiz.ui.theme.HomeFactIconBg
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlin.math.sign
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private enum class MainTab(val label: String) {
    HOME("Home"),
    LEARN("Learn"),
    STATS("Stats")
}

private val CarFunFacts = listOf(
    "The first practical car was built in 1886.",
    "Some electric cars can accelerate faster than sports cars.",
    "Car tires are made from more than just rubber.",
    "Seat belts save millions of lives every year.",
    "The first city speed limit was only 10 mph.",
    "Windshield wipers were invented in 1903.",
    "The rearview mirror became popular after racing.",
    "Most cars have over 30000 parts.",
    "Car paint has multiple layers for shine and protection.",
    "ABS helps cars stop safely on slippery roads.",
    "Hybrid cars use both electricity and fuel.",
    "Airbags can inflate in a fraction of a second.",
    "Cruise control helps keep steady highway speed.",
    "Electric cars have fewer moving engine parts.",
    "The horn was one of the earliest safety features.",
    "Some headlights turn with the steering wheel.",
    "Some cars can park themselves.",
    "Car GPS uses satellites high above Earth.",
    "A clean air filter helps engines run better.",
    "Regular tire checks make rides safer and smoother."
)

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
                        brands = BrandCatalog.allBrands,
                        onSwipedRight = {
                            totalSwipes += 1
                            rightSwipes += 1
                            onPositiveSwipe()
                            scope.launch {
                                statsRepository.recordFlashcards(
                                    rightGuessed = rightSwipes,
                                    totalSwipes = totalSwipes
                                )
                            }
                        },
                        onSwipedLeft = {
                            totalSwipes += 1
                            scope.launch {
                                statsRepository.recordFlashcards(
                                    rightGuessed = rightSwipes,
                                    totalSwipes = totalSwipes
                                )
                            }
                        }
                    )

                    MainTab.STATS -> StatsScreen(stats = persistedStats)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            BottomNav(tab = tab, onTabChange = { clicked ->
                val now = System.currentTimeMillis()
                if (clicked == MainTab.HOME) {
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
                } else {
                    showCongrats = false
                    if (clicked == MainTab.LEARN && tab != MainTab.LEARN) {
                        rightSwipes = 0
                        totalSwipes = 0
                    }
                }
                tab = clicked
            })
        }
    }
}

@Composable
private fun WelcomeScreen(onStart: () -> Unit) {
    val factIndex = remember { Random.nextInt(CarFunFacts.size) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(containerColor = AppSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.DirectionsCar,
                    contentDescription = null,
                    tint = BrandPrimary,
                    modifier = Modifier.size(132.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Hello, Explorer!", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold, color = AppTextPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ready to discover all the amazing cars in the world?",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = AppTextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedPillButton(label = "Play", textColor = BrandPrimary, icon = Icons.Filled.VideogameAsset, onClick = onStart)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = HomeFactCard)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(HomeFactIconBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Lightbulb, contentDescription = null, tint = AppSurface)
                }
                Column {
                    Text(
                        "Did you know?",
                        color = HomeFactIconBg,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        CarFunFacts[factIndex],
                        color = BrandTertiary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun LogoQuizScreen(
    quizSessionId: Int,
    onSpeakBrand: (String) -> Unit,
    onQuizComplete: (Int, Int) -> Unit
) {
    val engine = remember(quizSessionId) { QuizEngine(BrandCatalog.allBrands, totalQuestions = 10) }
    var question by remember(quizSessionId) { mutableStateOf(engine.nextQuestion()) }
    var selectedAnswerId by remember(quizSessionId) { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(question?.number) {
        question?.let { onSpeakBrand(it.promptBrand.displayName) }
    }

    if (question == null) {
        LaunchedEffect(quizSessionId) { onQuizComplete(engine.correctAnswers, engine.answeredQuestions) }
        return
    }

    val activeQuestion = question ?: return
    val progress = activeQuestion.number.toFloat() / activeQuestion.total

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Box(
            modifier = Modifier
                .size(88.dp)
                .background(BrandSecondarySoft, CircleShape)
                .align(Alignment.CenterHorizontally)
                .pointerInput(activeQuestion.number) {
                    detectDragGestures(
                        onDragStart = { onSpeakBrand(activeQuestion.promptBrand.displayName) },
                        onDrag = { _, _ -> })
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Speak brand", tint = BrandSecondary, modifier = Modifier.size(36.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Which one is the", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
        Text("${activeQuestion.promptBrand.displayName}?", style = MaterialTheme.typography.headlineLarge, color = BrandPrimary, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))
        activeQuestion.options.chunked(2).forEach { row ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                row.forEach { brand ->
                    val correct = brand.id == activeQuestion.correctBrandId
                    val selected = selectedAnswerId == brand.id
                    val border = when {
                        selected && correct -> AppSuccess
                        selected && !correct -> AppError
                        else -> AppBorderNeutral
                    }
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(170.dp),
                        shape = RoundedCornerShape(36.dp),
                        colors = CardDefaults.cardColors(containerColor = AppSurface),
                        border = BorderStroke(3.dp, border),
                        onClick = {
                            if (selectedAnswerId != null) return@Card
                            selectedAnswerId = brand.id
                            engine.submitAnswer(activeQuestion, brand.id)
                            scope.launch {
                                delay(650)
                                selectedAnswerId = null
                                question = engine.nextQuestion()
                            }
                        }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(modifier = Modifier
                                .size(64.dp)
                                .background(AppLogoTile, RoundedCornerShape(2.dp)), contentAlignment = Alignment.Center) {
                                BrandLogo(brand = brand, modifier = Modifier.fillMaxSize().padding(8.dp))
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = brand.displayName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = AppTextPrimary)
                        }
                    }
                }
                if (row.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text("Question ${activeQuestion.number} of ${activeQuestion.total}", color = BrandSecondary, fontWeight = FontWeight.Bold)
            Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = BrandTertiary)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .background(BrandSecondarySoft, RoundedCornerShape(999.dp))) {
            Box(modifier = Modifier
                .fillMaxWidth(progress)
                .height(16.dp)
                .background(BrandSecondary, RoundedCornerShape(999.dp)))
        }
    }
}

@Composable
private fun FlashcardDeckScreen(
    brands: List<CarBrand>,
    onSwipedRight: () -> Unit,
    onSwipedLeft: () -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    val xOffset = remember { Animatable(0f) }
    val yOffset = remember { Animatable(0f) }
    var deckSize by remember { mutableStateOf(IntSize.Zero) }
    val scope = rememberCoroutineScope()

    val current = brands[currentIndex]
    val next = brands[(currentIndex + 1) % brands.size]
    val rotation = (xOffset.value / 36f).coerceIn(-22f, 22f)
    val yayAlpha = (xOffset.value / 220f).coerceIn(0f, 1f)
    val nayAlpha = (-xOffset.value / 220f).coerceIn(0f, 1f)

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Swipe cards right if she knows it", style = MaterialTheme.typography.headlineSmall, color = AppTextSecondary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
                .onSizeChanged { deckSize = it },
            contentAlignment = Alignment.Center
        ) {
            FlashCardContent(
                brand = next,
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .wrapContentHeight()
                    .graphicsLayer {
                        scaleX = 0.94f
                        scaleY = 0.94f
                        translationY = 30f
                        alpha = 0.9f
                    }
            )

            FlashCardContent(
                brand = current,
                modifier = Modifier
                    .fillMaxWidth(0.94f)
                    .offset { IntOffset(xOffset.value.roundToInt(), yOffset.value.roundToInt()) }
                    .graphicsLayer { rotationZ = rotation }
                    .pointerInput(currentIndex, deckSize) {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                scope.launch {
                                    xOffset.snapTo(xOffset.value + dragAmount.x)
                                    yOffset.snapTo(yOffset.value + dragAmount.y * 0.24f)
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    val threshold = deckSize.width * 0.25f
                                    if (abs(xOffset.value) > threshold) {
                                        val direction =
                                            sign(xOffset.value).takeIf { it != 0f } ?: 1f
                                        xOffset.animateTo(
                                            direction * deckSize.width * 1.4f,
                                            tween(220)
                                        )
                                        yOffset.animateTo(yOffset.value + 90f, tween(220))
                                        if (direction > 0f) onSwipedRight() else onSwipedLeft()
                                        currentIndex = (currentIndex + 1) % brands.size
                                        xOffset.snapTo(0f)
                                        yOffset.snapTo(0f)
                                    } else {
                                        xOffset.animateTo(
                                            0f,
                                            spring(stiffness = 500f, dampingRatio = 0.75f)
                                        )
                                        yOffset.animateTo(
                                            0f,
                                            spring(stiffness = 500f, dampingRatio = 0.75f)
                                        )
                                    }
                                }
                            }
                        )
                    }
            )

            Text("YAY", modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 26.dp, y = 34.dp)
                .graphicsLayer { alpha = yayAlpha }, color = BrandTertiary, fontWeight = FontWeight.Black, fontSize = 32.sp)
            Text("NAY", modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-26).dp, y = 34.dp)
                .graphicsLayer { alpha = nayAlpha }, color = BrandPrimary, fontWeight = FontWeight.Black, fontSize = 32.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Card ${currentIndex + 1}/${brands.size}", color = BrandSecondary, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
private fun FlashCardContent(brand: CarBrand, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(400.dp).padding(24.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .size(170.dp)
                .background(AppLogoTile, RoundedCornerShape(4.dp)), contentAlignment = Alignment.Center) {
                BrandLogo(brand = brand, modifier = Modifier.fillMaxSize().padding(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(brand.displayName, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun StatsScreen(stats: PersistedStats) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            "Last Game Stats",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = AppTextPrimary
        )

        StatsGroupCard(
            borderColor = BrandPrimary,
            topTitle = "Quiz\nResults",
            topValue = "${stats.lastQuizCorrect}/${stats.lastQuizTotal}",
            topSubtitle = if (stats.lastQuizTotal == 0) "No games yet" else "Great Job!",
            topPercent = "${stats.lastQuizAccuracy}%",
            bottomTitle = "Flashcards",
            bottomValue = "${stats.lastFlashcardsGuessed}/${stats.lastFlashcardsTotal}",
            bottomSubtitle = "Guessed",
            bottomPercent = "${stats.lastFlashcardsAccuracy}%"
        )

        Text(
            "All Time Best",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = AppTextPrimary
        )

        StatsGroupCard(
            borderColor = BrandTertiary,
            topTitle = "Top\nScore",
            topValue = "${stats.bestQuizCorrect}/${stats.bestQuizTotal}",
            topSubtitle = if (stats.bestQuizTotal == 0) "No games yet" else "Quiz Master",
            topPercent = "${stats.bestQuizAccuracy}%",
            bottomTitle = "Max\nGuessed",
            bottomValue = "${stats.bestFlashcardsGuessed}/${stats.bestFlashcardsTotal}",
            bottomSubtitle = "In one go",
            bottomPercent = "${stats.bestFlashcardsAccuracy}%"
        )
    }
}

@Composable
private fun StatsGroupCard(
    borderColor: Color,
    topTitle: String,
    topValue: String,
    topSubtitle: String,
    topPercent: String,
    bottomTitle: String,
    bottomValue: String,
    bottomSubtitle: String,
    bottomPercent: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = AppSurface),
        border = BorderStroke(4.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
            StatsRow(topTitle, topValue, topSubtitle, topPercent, borderColor)
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(AppSurfaceSoft)
            )
            StatsRow(bottomTitle, bottomValue, bottomSubtitle, bottomPercent, BrandSecondary)
        }
    }
}

@Composable
private fun StatsRow(
    title: String,
    value: String,
    subtitle: String,
    percent: String,
    percentColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, lineHeight = 30.sp)
            Text(subtitle, style = MaterialTheme.typography.titleMedium, color = AppTextSecondary)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(value, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
            Text(percent, style = MaterialTheme.typography.titleLarge, color = percentColor, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
private fun CongratsScreen(score: Int, total: Int, onPlayAgain: () -> Unit, onHome: () -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .size(140.dp)
            .background(BrandPrimarySoft, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Filled.Star, contentDescription = null, tint = AppSurface, modifier = Modifier.size(86.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Great Job!", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(16.dp))

        Card(shape = RoundedCornerShape(999.dp), colors = CardDefaults.cardColors(containerColor = AppSurfaceSoft)) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Filled.AutoAwesome, contentDescription = null, tint = BrandSecondary)
                Text(text = "You found $score of $total cars today!", color = BrandSecondary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Icon(Icons.Filled.DirectionsCar, contentDescription = null, tint = BrandSecondary, modifier = Modifier.size(120.dp))
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedPillButton("Play Again", BrandPrimary, Icons.Filled.AutoAwesome, onPlayAgain)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedPillButton("Home", BrandSecondary, Icons.Filled.Home, onHome)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun OutlinedPillButton(
    label: String,
    textColor: Color,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(999.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppSurface,
            contentColor = textColor
        ),
        border = BorderStroke(3.dp, textColor)
    ) {
        Icon(icon, contentDescription = null, tint = textColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(label, color = textColor, fontWeight = FontWeight.ExtraBold, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
private fun BottomNav(tab: MainTab, onTabChange: (MainTab) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(34.dp),
        color = Color.Transparent,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(MainTab.HOME, tab == MainTab.HOME, onTabChange)
            NavItem(MainTab.LEARN, tab == MainTab.LEARN, onTabChange)
            NavItem(MainTab.STATS, tab == MainTab.STATS, onTabChange)
        }
    }
}

@Composable
private fun NavItem(item: MainTab, selected: Boolean, onTabChange: (MainTab) -> Unit) {
    val color = if (selected) BrandPrimary else BrandSecondary

    Surface(shape = RoundedCornerShape(999.dp), color = AppSurfaceTint, onClick = { onTabChange(item) }) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = when (item) {
                    MainTab.HOME -> Icons.Filled.Home
                    MainTab.LEARN -> Icons.Filled.Style
                    MainTab.STATS -> Icons.Filled.BarChart
                },
                contentDescription = null,
                tint = color
            )
            Text(item.label, color = color, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview
@Composable
fun StatsPreview() {
    CarQuizTheme {
        StatsScreen(
            stats = PersistedStats(
                lastQuizCorrect = 8,
                lastQuizTotal = 10,
                lastFlashcardsGuessed = 12,
                lastFlashcardsTotal = 15,
                bestQuizCorrect = 10,
                bestQuizTotal = 10,
                bestFlashcardsGuessed = 45,
                bestFlashcardsTotal = 50
            )
        )
    }
}

@Composable
private fun BrandLogo(brand: CarBrand, modifier: Modifier = Modifier) {
    require(brand.logo != 0) { "Missing logo dd for brand: ${brand.id}" }
    Image(
        painter = painterResource(id = brand.logo),
        contentDescription = brand.displayName,
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}
