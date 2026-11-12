package com.example

import com.example.data.generator.EnglishEasyQuestions
import com.example.data.generator.EnglishHardQuestions
import com.example.data.generator.EnglishMediumQuestions
import com.example.data.generator.EnglishQuestionBank
import com.example.data.generator.MathQuestionGenerator
import com.example.data.model.Difficulty
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameLogicTest {

    @Test
    fun englishQuestions_haveAtLeast60PerDifficulty() {
        assertTrue(
            "Easy questions count must be >= 60, was ${EnglishEasyQuestions.list.size}",
            EnglishEasyQuestions.list.size >= 60
        )
        assertTrue(
            "Medium questions count must be >= 60, was ${EnglishMediumQuestions.list.size}",
            EnglishMediumQuestions.list.size >= 60
        )
        assertTrue(
            "Hard questions count must be >= 60, was ${EnglishHardQuestions.list.size}",
            EnglishHardQuestions.list.size >= 60
        )
    }

    @Test
    fun englishQuestions_allHaveValidOptionsAndDistinctAnswers() {
        val allLists = listOf(
            EnglishEasyQuestions.list,
            EnglishMediumQuestions.list,
            EnglishHardQuestions.list
        )

        for (list in allLists) {
            for (q in list) {
                assertEquals("Question '${q.prompt}' must have 4 options", 4, q.options.size)
                assertTrue("Question '${q.prompt}' correctIndex must be 0..3", q.correctIndex in 0..3)
                val distinctOptions = q.options.toSet()
                assertEquals("Options in '${q.prompt}' must all be unique", 4, distinctOptions.size)
            }
        }
    }

    @Test
    fun mathQuestionGenerator_producesValidQuestionsForDifficulties() {
        for (diff in Difficulty.entries) {
            for (i in 0 until 50) {
                val q = MathQuestionGenerator.generateQuestion(diff)
                assertEquals("Math question must have 4 options", 4, q.options.size)
                assertTrue("Math correctIndex must be in 0..3", q.correctIndex in 0..3)
                val uniqueOptions = q.options.toSet()
                assertEquals("Math options must all be unique for '${q.prompt}'", 4, uniqueOptions.size)
            }
        }
    }

    @Test
    fun englishQuestionBank_shufflesAndAvoidsConsecutiveRepeats() {
        var lastPrompt: String? = null
        for (i in 0 until 40) {
            val q = EnglishQuestionBank.getQuestion(Difficulty.EASY)
            assertEquals(4, q.options.size)
            assertTrue(q.correctIndex in 0..3)
            if (lastPrompt != null) {
                assertNotEquals("Consecutive questions should not have the same prompt", lastPrompt, q.prompt)
            }
            lastPrompt = q.prompt
        }
    }
}
