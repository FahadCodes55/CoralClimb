package com.example.data.generator

import com.example.data.model.Difficulty
import com.example.data.model.Question
import kotlin.random.Random

object MathQuestionGenerator {
    private var lastPrompt: String? = null

    fun generateQuestion(difficulty: Difficulty): Question {
        var attempts = 0
        while (attempts < 10) {
            val q = when (difficulty) {
                Difficulty.EASY -> generateEasy()
                Difficulty.MEDIUM -> generateMedium()
                Difficulty.HARD -> generateHard()
            }
            if (q.prompt != lastPrompt) {
                lastPrompt = q.prompt
                return q
            }
            attempts++
        }
        return generateEasy()
    }

    private fun generateEasy(): Question {
        val op = Random.nextInt(3)
        return when (op) {
            0 -> {
                // Addition up to 20
                val sum = Random.nextInt(5, 21)
                val a = Random.nextInt(1, sum)
                val b = sum - a
                buildQuestion("$a + $b = ?", sum)
            }
            1 -> {
                // Subtraction up to 20
                val a = Random.nextInt(4, 21)
                val b = Random.nextInt(1, a)
                val diff = a - b
                buildQuestion("$a - $b = ?", diff)
            }
            else -> {
                // Missing number up to 20
                val a = Random.nextInt(2, 16)
                val b = Random.nextInt(1, 20 - a + 1)
                val sum = a + b
                buildQuestion("$a + ? = $sum", b)
            }
        }
    }

    private fun generateMedium(): Question {
        val op = Random.nextInt(4)
        return when (op) {
            0 -> {
                // Addition up to 100
                val a = Random.nextInt(15, 60)
                val b = Random.nextInt(10, 40)
                buildQuestion("$a + $b = ?", a + b)
            }
            1 -> {
                // Subtraction up to 100
                val a = Random.nextInt(30, 99)
                val b = Random.nextInt(10, a - 5)
                buildQuestion("$a - $b = ?", a - b)
            }
            2 -> {
                // Tables 2, 3, 4, 5, 10
                val tables = listOf(2, 3, 4, 5, 10)
                val factorA = tables.random()
                val factorB = Random.nextInt(2, 11)
                buildQuestion("$factorA × $factorB = ?", factorA * factorB)
            }
            else -> {
                // Missing factor or sum
                val a = Random.nextInt(20, 80)
                val target = Random.nextInt(a + 5, 99)
                val missing = target - a
                buildQuestion("$a + ? = $target", missing)
            }
        }
    }

    private fun generateHard(): Question {
        val op = Random.nextInt(4)
        return when (op) {
            0 -> {
                // Multiplication 6x6 to 12x12
                val a = Random.nextInt(6, 13)
                val b = Random.nextInt(4, 13)
                buildQuestion("$a × $b = ?", a * b)
            }
            1 -> {
                // Division
                val divisor = Random.nextInt(3, 11)
                val quotient = Random.nextInt(3, 12)
                val dividend = divisor * quotient
                buildQuestion("$dividend ÷ $divisor = ?", quotient)
            }
            2 -> {
                // Two-step: (a × b) + c
                val a = Random.nextInt(3, 8)
                val b = Random.nextInt(3, 7)
                val c = Random.nextInt(2, 15)
                val result = (a * b) + c
                buildQuestion("($a × $b) + $c = ?", result)
            }
            else -> {
                // Two-step: a + (b × c) or (a - b) × c
                val a = Random.nextInt(10, 30)
                val b = Random.nextInt(2, 6)
                val c = Random.nextInt(2, 6)
                val result = a + (b * c)
                buildQuestion("$a + ($b × $c) = ?", result)
            }
        }
    }

    private fun buildQuestion(prompt: String, correctVal: Int): Question {
        val distractors = mutableSetOf<Int>()
        val offsets = listOf(1, -1, 2, -2, 10, -10, 5, -5, 3, -3)

        for (offset in offsets.shuffled()) {
            val candidate = correctVal + offset
            if (candidate >= 0 && candidate != correctVal) {
                distractors.add(candidate)
            }
            if (distractors.size >= 3) break
        }

        var extra = 1
        while (distractors.size < 3) {
            val candidate = if (correctVal > 5) correctVal + extra else correctVal + extra + 3
            if (candidate >= 0 && candidate != correctVal) {
                distractors.add(candidate)
            }
            extra++
        }

        val allOptions = (distractors.take(3) + correctVal).shuffled()
        val correctIndex = allOptions.indexOf(correctVal)

        return Question(
            prompt = prompt,
            options = allOptions.map { it.toString() },
            correctIndex = correctIndex
        )
    }
}
