package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

class SoundManager {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    var isMuted: Boolean = false

    private val sampleRate = 44100

    fun playCorrect() {
        if (isMuted) return
        scope.launch {
            // Ascending major chord (C5, E5, G5)
            val notes = listOf(523.25 to 70, 659.25 to 70, 783.99 to 140)
            val fullPcm = mutableListOf<Short>()
            for ((freq, durationMs) in notes) {
                fullPcm.addAll(generateSineTone(freq, durationMs, 0.45f))
            }
            playPcm(fullPcm.toShortArray())
        }
    }

    fun playPop() {
        if (isMuted) return
        scope.launch {
            // Bubble pop: rising frequency with fast decay
            val durationMs = 60
            val samples = (sampleRate * (durationMs / 1000.0)).toInt()
            val pcm = ShortArray(samples)
            for (i in 0 until samples) {
                val progress = i.toDouble() / samples
                val freq = 320.0 + progress * 400.0 // 320Hz to 720Hz
                val envelope = (1.0 - progress) * (1.0 - progress)
                val sample = sin(2.0 * PI * freq * i / sampleRate) * envelope * 0.4
                pcm[i] = (sample * Short.MAX_VALUE).toInt().toShort()
            }
            playPcm(pcm)
        }
    }

    fun playWrong() {
        if (isMuted) return
        scope.launch {
            // Soft friendly "boing" / wobble - low, reassuring, not jarring
            val notes = listOf(260.0 to 90, 200.0 to 120)
            val fullPcm = mutableListOf<Short>()
            for ((freq, durationMs) in notes) {
                fullPcm.addAll(generateSineTone(freq, durationMs, 0.35f))
            }
            playPcm(fullPcm.toShortArray())
        }
    }

    fun playVictory() {
        if (isMuted) return
        scope.launch {
            // Celebratory fanfare (C5, E5, G5, C6)
            val notes = listOf(523.25 to 90, 659.25 to 90, 783.99 to 110, 1046.50 to 220)
            val fullPcm = mutableListOf<Short>()
            for ((freq, durationMs) in notes) {
                fullPcm.addAll(generateSineTone(freq, durationMs, 0.5f))
            }
            playPcm(fullPcm.toShortArray())
        }
    }

    private fun generateSineTone(freq: Double, durationMs: Int, volume: Float): List<Short> {
        val samples = (sampleRate * (durationMs / 1000.0)).toInt()
        val result = ArrayList<Short>(samples)
        for (i in 0 until samples) {
            val progress = i.toDouble() / samples
            // Smooth attack and release envelope to prevent clicking
            val attack = (progress * 10).coerceAtMost(1.0)
            val release = ((1.0 - progress) * 6).coerceIn(0.0, 1.0)
            val envelope = attack * release
            val sample = sin(2.0 * PI * freq * i / sampleRate) * envelope * volume
            result.add((sample * Short.MAX_VALUE).toInt().toShort())
        }
        return result
    }

    private fun playPcm(pcm: ShortArray) {
        try {
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(pcm.size * 2)

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_GAME)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(pcm, 0, pcm.size)
            audioTrack.play()

            // Release after playback
            val durationMs = (pcm.size * 1000L) / sampleRate + 50
            Thread.sleep(durationMs)
            audioTrack.stop()
            audioTrack.release()
        } catch (_: Exception) {
            // AudioTrack fallback safety
        }
    }
}
