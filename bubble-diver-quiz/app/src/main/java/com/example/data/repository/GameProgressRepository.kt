package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.Difficulty
import com.example.data.model.GameMode

class GameProgressRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("coral_climb_prefs", Context.MODE_PRIVATE)

    init {
        val oldPrefs = context.getSharedPreferences("bubble_diver_quiz_prefs", Context.MODE_PRIVATE)
        if (oldPrefs.contains("key_total_stars") && !prefs.contains("key_total_stars")) {
            prefs.edit()
                .putInt("key_total_stars", oldPrefs.getInt("key_total_stars", 0))
                .putBoolean("key_is_muted", oldPrefs.getBoolean("key_is_muted", false))
                .apply()
        }
    }

    fun getTotalStars(): Int = prefs.getInt("key_total_stars", 0)

    fun addStars(stars: Int): Int {
        val current = getTotalStars()
        val updated = current + stars
        prefs.edit().putInt("key_total_stars", updated).apply()
        return updated
    }

    fun isMuted(): Boolean = prefs.getBoolean("key_is_muted", false)

    fun setMuted(muted: Boolean) {
        prefs.edit().putBoolean("key_is_muted", muted).apply()
    }

    fun getBestScore(mode: GameMode, difficulty: Difficulty): Int {
        val key = "best_${mode.name}_${difficulty.name}"
        return prefs.getInt(key, 0)
    }

    fun updateBestScore(mode: GameMode, difficulty: Difficulty, score: Int) {
        val key = "best_${mode.name}_${difficulty.name}"
        val current = prefs.getInt(key, 0)
        if (score > current) {
            prefs.edit().putInt(key, score).apply()
        }
    }

    fun getHighestRoundCompleted(mode: GameMode, difficulty: Difficulty): Int {
        val key = "round_${mode.name}_${difficulty.name}"
        return prefs.getInt(key, 0)
    }

    fun recordRoundCompleted(mode: GameMode, difficulty: Difficulty, round: Int) {
        val key = "round_${mode.name}_${difficulty.name}"
        val current = prefs.getInt(key, 0)
        if (round > current) {
            prefs.edit().putInt(key, round).apply()
        }
    }
}
