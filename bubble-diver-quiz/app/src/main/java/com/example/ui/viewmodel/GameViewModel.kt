package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.SoundManager
import com.example.data.generator.EnglishQuestionBank
import com.example.data.generator.MathQuestionGenerator
import com.example.data.model.Difficulty
import com.example.data.model.DiverAnimationState
import com.example.data.model.GameMode
import com.example.data.model.Question
import com.example.data.model.RoundStats
import com.example.data.repository.GameProgressRepository
import com.example.ui.components.BubbleState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface GameScreenState {
    data object Home : GameScreenState
    data object Playing : GameScreenState
    data class RoundComplete(val stats: RoundStats) : GameScreenState
    data object GameOver : GameScreenState
}

data class GameUiState(
    val screen: GameScreenState = GameScreenState.Home,
    val selectedMode: GameMode = GameMode.MATH,
    val selectedDifficulty: Difficulty = Difficulty.EASY,
    val totalStars: Int = 0,
    val isMuted: Boolean = false,

    // Round gameplay state
    val roundNumber: Int = 1,
    val currentQuestion: Question? = null,
    val currentStep: Int = 0, // 0 to 8
    val totalSteps: Int = 8,
    val lives: Int = 3,
    val mistakesThisRound: Int = 0,
    val timerRemainingMs: Long = 25000L,
    val timerTotalMs: Long = 25000L,

    // Feedback & Animations
    val bubbleStates: List<BubbleState> = List(4) { BubbleState.DEFAULT },
    val diverAnimation: DiverAnimationState = DiverAnimationState.IDLE,
    val feedbackMessage: String? = null,
    val isProcessingAnswer: Boolean = false
)

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameProgressRepository(application.applicationContext)
    val soundManager = SoundManager()

    private val _uiState = MutableStateFlow(
        GameUiState(
            totalStars = repository.getTotalStars(),
            isMuted = repository.isMuted()
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        soundManager.isMuted = repository.isMuted()
    }

    fun selectMode(mode: GameMode) {
        _uiState.update { it.copy(selectedMode = mode) }
    }

    fun selectDifficulty(difficulty: Difficulty) {
        _uiState.update { it.copy(selectedDifficulty = difficulty) }
    }

    fun toggleMute() {
        val newMuted = !_uiState.value.isMuted
        repository.setMuted(newMuted)
        soundManager.isMuted = newMuted
        _uiState.update { it.copy(isMuted = newMuted) }
    }

    fun startNewGame() {
        startRound(roundNum = 1)
    }

    fun nextRound() {
        val nextRoundNum = _uiState.value.roundNumber + 1
        startRound(roundNum = nextRoundNum)
    }

    fun retryRound() {
        startRound(roundNum = _uiState.value.roundNumber)
    }

    fun returnToHome() {
        timerJob?.cancel()
        _uiState.update {
            it.copy(
                screen = GameScreenState.Home,
                totalStars = repository.getTotalStars(),
                feedbackMessage = null,
                isProcessingAnswer = false
            )
        }
    }

    private fun startRound(roundNum: Int) {
        timerJob?.cancel()
        val difficulty = _uiState.value.selectedDifficulty

        // Calculate timer duration: base seconds minus (roundNum - 1) * 1.5s, with a gentle minimum of 12s
        val baseSeconds = difficulty.baseSeconds
        val reduction = ((roundNum - 1) * 1.5f).toInt()
        val roundSeconds = (baseSeconds - reduction).coerceAtLeast(12)
        val roundDurationMs = roundSeconds * 1000L

        EnglishQuestionBank.resetHistory()
        val firstQuestion = fetchNextQuestion()

        _uiState.update {
            it.copy(
                screen = GameScreenState.Playing,
                roundNumber = roundNum,
                currentStep = 0,
                lives = 3,
                mistakesThisRound = 0,
                currentQuestion = firstQuestion,
                timerTotalMs = roundDurationMs,
                timerRemainingMs = roundDurationMs,
                bubbleStates = List(4) { BubbleState.DEFAULT },
                diverAnimation = DiverAnimationState.IDLE,
                feedbackMessage = null,
                isProcessingAnswer = false
            )
        }

        startTimer()
    }

    private fun fetchNextQuestion(): Question {
        return when (_uiState.value.selectedMode) {
            GameMode.MATH -> MathQuestionGenerator.generateQuestion(_uiState.value.selectedDifficulty)
            GameMode.ENGLISH -> EnglishQuestionBank.getQuestion(_uiState.value.selectedDifficulty)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            val tickInterval = 100L
            while (_uiState.value.timerRemainingMs > 0 && _uiState.value.screen == GameScreenState.Playing) {
                delay(tickInterval)
                if (!_uiState.value.isProcessingAnswer) {
                    _uiState.update {
                        val newTime = (it.timerRemainingMs - tickInterval).coerceAtLeast(0L)
                        it.copy(timerRemainingMs = newTime)
                    }
                }
            }

            if (_uiState.value.timerRemainingMs <= 0 && _uiState.value.screen == GameScreenState.Playing) {
                handleTimeout()
            }
        }
    }

    fun onAnswerClicked(optionIndex: Int) {
        val state = _uiState.value
        if (state.isProcessingAnswer || state.screen != GameScreenState.Playing) return
        val question = state.currentQuestion ?: return

        if (optionIndex == question.correctIndex) {
            handleCorrectAnswer(optionIndex)
        } else {
            handleWrongAnswer(optionIndex)
        }
    }

    private fun handleCorrectAnswer(optionIndex: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingAnswer = true) }
            soundManager.playPop()
            soundManager.playCorrect()

            // 1. Pop bubble & animate diver upward
            val updatedBubbles = _uiState.value.bubbleStates.mapIndexed { i, s ->
                if (i == optionIndex) BubbleState.CORRECT_POPPING else BubbleState.DISABLED
            }
            val newStep = _uiState.value.currentStep + 1

            _uiState.update {
                it.copy(
                    bubbleStates = updatedBubbles,
                    currentStep = newStep,
                    diverAnimation = DiverAnimationState.HAPPY_BOUNCE,
                    feedbackMessage = getRandomPraise()
                )
            }

            delay(650)

            // Check if round complete (8 correct answers)
            if (newStep >= _uiState.value.totalSteps) {
                timerJob?.cancel()
                val mistakes = _uiState.value.mistakesThisRound
                val stars = when {
                    mistakes == 0 -> 3
                    mistakes <= 2 -> 2
                    else -> 1
                }

                repository.addStars(stars)
                repository.recordRoundCompleted(
                    _uiState.value.selectedMode,
                    _uiState.value.selectedDifficulty,
                    _uiState.value.roundNumber
                )
                repository.updateBestScore(
                    _uiState.value.selectedMode,
                    _uiState.value.selectedDifficulty,
                    newStep * 100
                )

                soundManager.playVictory()

                val stats = RoundStats(
                    roundNumber = _uiState.value.roundNumber,
                    totalQuestions = _uiState.value.totalSteps,
                    correctCount = newStep,
                    mistakes = mistakes,
                    starsEarned = stars
                )

                _uiState.update {
                    it.copy(
                        screen = GameScreenState.RoundComplete(stats),
                        totalStars = repository.getTotalStars(),
                        isProcessingAnswer = false,
                        feedbackMessage = null
                    )
                }
            } else {
                // Next question
                val nextQ = fetchNextQuestion()
                _uiState.update {
                    it.copy(
                        currentQuestion = nextQ,
                        bubbleStates = List(4) { BubbleState.DEFAULT },
                        diverAnimation = DiverAnimationState.IDLE,
                        isProcessingAnswer = false,
                        feedbackMessage = null
                    )
                }
            }
        }
    }

    private fun handleWrongAnswer(optionIndex: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isProcessingAnswer = true) }
            soundManager.playWrong()

            val newLives = _uiState.value.lives - 1
            val newMistakes = _uiState.value.mistakesThisRound + 1

            val updatedBubbles = _uiState.value.bubbleStates.mapIndexed { i, s ->
                if (i == optionIndex) BubbleState.WRONG_WOBBLE else s
            }

            _uiState.update {
                it.copy(
                    lives = newLives,
                    mistakesThisRound = newMistakes,
                    bubbleStates = updatedBubbles,
                    diverAnimation = DiverAnimationState.WRONG_WOBBLE,
                    feedbackMessage = "Try again!"
                )
            }

            delay(700)

            if (newLives <= 0) {
                handleGameOver()
            } else {
                // Reset bubble wobble so child can try again
                val resetBubbles = _uiState.value.bubbleStates.mapIndexed { i, s ->
                    if (i == optionIndex) BubbleState.DISABLED else s
                }
                _uiState.update {
                    it.copy(
                        bubbleStates = resetBubbles,
                        diverAnimation = DiverAnimationState.IDLE,
                        isProcessingAnswer = false,
                        feedbackMessage = null
                    )
                }
            }
        }
    }

    private fun handleTimeout() {
        viewModelScope.launch {
            soundManager.playWrong()
            _uiState.update {
                it.copy(
                    diverAnimation = DiverAnimationState.FALL_DOWN,
                    feedbackMessage = "Time's up!"
                )
            }
            delay(1000)
            handleGameOver()
        }
    }

    private fun handleGameOver() {
        timerJob?.cancel()
        _uiState.update {
            it.copy(
                screen = GameScreenState.GameOver,
                diverAnimation = DiverAnimationState.FALL_DOWN,
                isProcessingAnswer = false,
                feedbackMessage = null
            )
        }
    }

    private fun getRandomPraise(): String {
        val praises = listOf("Great job!", "Awesome!", "Super!", "Splendid!", "You got it!", "Fantastic!")
        return praises.random()
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
