package com.example.data.generator

import com.example.data.model.Difficulty
import com.example.data.model.Question

object EnglishQuestionBank {
    private var lastPrompt: String? = null
    private val usedIndices = mutableMapOf<Difficulty, MutableSet<Int>>()

    fun getQuestion(difficulty: Difficulty): Question {
        val pool = when (difficulty) {
            Difficulty.EASY -> EnglishEasyQuestions.list
            Difficulty.MEDIUM -> EnglishMediumQuestions.list
            Difficulty.HARD -> EnglishHardQuestions.list
        }

        val used = usedIndices.getOrPut(difficulty) { mutableSetOf() }
        if (used.size >= pool.size) {
            used.clear()
        }

        val available = pool.indices.filter { it !in used }
        var chosenIndex = available.random()
        var chosenQuestion = pool[chosenIndex]

        // Ensure not the same prompt twice in a row
        if (chosenQuestion.prompt == lastPrompt && available.size > 1) {
            val altAvailable = available.filter { pool[it].prompt != lastPrompt }
            if (altAvailable.isNotEmpty()) {
                chosenIndex = altAvailable.random()
                chosenQuestion = pool[chosenIndex]
            }
        }

        used.add(chosenIndex)
        lastPrompt = chosenQuestion.prompt

        // Shuffle the options so the correct answer is not always in position 0
        val correctValue = chosenQuestion.options[chosenQuestion.correctIndex]
        val shuffledOptions = chosenQuestion.options.shuffled()
        val newCorrectIndex = shuffledOptions.indexOf(correctValue)

        return Question(
            prompt = chosenQuestion.prompt,
            options = shuffledOptions,
            correctIndex = newCorrectIndex,
            hint = chosenQuestion.hint
        )
    }

    fun resetHistory() {
        usedIndices.clear()
        lastPrompt = null
    }
}
