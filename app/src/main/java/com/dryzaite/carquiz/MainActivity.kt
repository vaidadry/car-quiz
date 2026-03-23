package com.dryzaite.carquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.dryzaite.carquiz.data.repository.InMemoryCarQuizRepository
import com.dryzaite.carquiz.domain.usecase.GenerateQuizQuestionUseCase
import com.dryzaite.carquiz.domain.usecase.SubmitAnswerUseCase
import com.dryzaite.carquiz.ui.quiz.QuizScreen
import com.dryzaite.carquiz.ui.quiz.QuizViewModel
import com.dryzaite.carquiz.ui.theme.CarQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = InMemoryCarQuizRepository()
        val viewModel = QuizViewModel(
            generateQuizQuestionUseCase = GenerateQuizQuestionUseCase(repository),
            submitAnswerUseCase = SubmitAnswerUseCase(),
        )

        setContent {
            CarQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    QuizScreen(
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    )
                }
            }
        }
    }
}
