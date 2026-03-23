package com.dryzaite.carquiz.ui.quiz

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState

@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val question = uiState.question

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "What brand is this?",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = question?.logoBrand?.logoEmoji ?: "🚗",
            style = MaterialTheme.typography.displayLarge,
        )

        Spacer(modifier = Modifier.height(20.dp))

        question?.options.orEmpty().forEach { option ->
            Button(
                onClick = { viewModel.onAnswerSelected(option.id) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = option.name)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        uiState.feedbackMessage?.let { feedback ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = feedback,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = viewModel::nextQuestion) {
                Text("Next")
            }
        }
    }
}
