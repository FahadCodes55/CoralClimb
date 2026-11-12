package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.LivingSeaBackground
import com.example.ui.screens.GameOverScreen
import com.example.ui.screens.GameScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.RoundCompleteScreen
import com.example.ui.theme.BubbleDiverQuizTheme
import com.example.ui.viewmodel.GameScreenState
import com.example.ui.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BubbleDiverQuizTheme {
                BubbleDiverApp()
            }
        }
    }
}

@Composable
fun BubbleDiverApp(
    viewModel: GameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Handle Android system back gesture to navigate gracefully back to Home
    BackHandler(enabled = uiState.screen != GameScreenState.Home) {
        viewModel.returnToHome()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = uiState.screen) {
                is GameScreenState.Home -> {
                    LivingSeaBackground {
                        HomeScreen(
                            totalStars = uiState.totalStars,
                            selectedMode = uiState.selectedMode,
                            selectedDifficulty = uiState.selectedDifficulty,
                            isMuted = uiState.isMuted,
                            onSelectMode = { viewModel.selectMode(it) },
                            onSelectDifficulty = { viewModel.selectDifficulty(it) },
                            onToggleMute = { viewModel.toggleMute() },
                            onStartGame = { viewModel.startNewGame() }
                        )
                    }
                }

                is GameScreenState.Playing -> {
                    LivingSeaBackground {
                        GameScreen(
                            state = uiState,
                            onAnswerClicked = { optionIndex ->
                                viewModel.onAnswerClicked(optionIndex)
                            },
                            onToggleMute = { viewModel.toggleMute() },
                            onExitClicked = { viewModel.returnToHome() }
                        )
                    }
                }

                is GameScreenState.RoundComplete -> {
                    RoundCompleteScreen(
                        stats = screen.stats,
                        totalStars = uiState.totalStars,
                        onNextRound = { viewModel.nextRound() },
                        onHomeClicked = { viewModel.returnToHome() }
                    )
                }

                is GameScreenState.GameOver -> {
                    LivingSeaBackground {
                        GameOverScreen(
                            onRetry = { viewModel.retryRound() },
                            onHome = { viewModel.returnToHome() }
                        )
                    }
                }
            }
        }
    }
}
