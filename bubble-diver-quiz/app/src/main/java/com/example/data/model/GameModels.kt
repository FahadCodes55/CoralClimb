package com.example.data.model

enum class GameMode(val displayName: String, val ageRange: String) {
    MATH("Math", "Numbers & Logic"),
    ENGLISH("English", "Words & Reading")
}

enum class Difficulty(val displayName: String, val subtitle: String, val baseSeconds: Int) {
    EASY("Easy", "Ages 6-7", 25),
    MEDIUM("Medium", "Ages 8-9", 22),
    HARD("Hard", "Ages 10-11", 18)
}

data class Question(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val hint: String = ""
)

enum class DiverAnimationState {
    IDLE,
    SWIM_UP,
    HAPPY_BOUNCE,
    WRONG_WOBBLE,
    FALL_DOWN
}

data class RoundStats(
    val roundNumber: Int,
    val totalQuestions: Int = 8,
    val correctCount: Int,
    val mistakes: Int,
    val starsEarned: Int
)
