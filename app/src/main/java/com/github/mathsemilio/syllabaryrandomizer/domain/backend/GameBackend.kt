/*
Copyright 2020 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.github.mathsemilio.syllabaryrandomizer.domain.backend

import kotlin.random.Random
import android.os.CountDownTimer
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.github.mathsemilio.syllabaryrandomizer.common.observable.BaseObservable
import com.github.mathsemilio.syllabaryrandomizer.common.util.generateRomanizationOptions
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty
import com.github.mathsemilio.syllabaryrandomizer.common.util.flavorBasedSyllabarySymbols

class GameBackend : BaseObservable<GameBackendListener>() {

    private val syllabarySymbols = flavorBasedSyllabarySymbols.toMutableList()

    private lateinit var countDownTimer: CountDownTimer
    private var totalCountdownTime = 0L
    private var countdownTime = 0L

    private var score = 0

    private lateinit var firstRomanizationOption: String
    private lateinit var secondRomanizationOption: String
    private lateinit var thirdRomanizationOption: String
    private lateinit var fourthRomanizationOption: String

    fun startGameOn(difficulty: GameDifficulty) {
        totalCountdownTime = difficulty.countdownTime

        syllabarySymbols.shuffle()

        notify { listener -> listener.onScoreUpdated(score) }
        notify { listener -> listener.onSymbolUpdated(syllabarySymbols.first()) }

        generateRomanizationOptions()
        notifyRomanizationOptionsGenerated()
        startCountdownTimerWith(totalCountdownTime)
    }

    private fun notifyRomanizationOptionsGenerated() {
        notify { listener ->
            listener.onRomanizationOptionsUpdated(
                listOf(
                    firstRomanizationOption,
                    secondRomanizationOption,
                    thirdRomanizationOption,
                    fourthRomanizationOption
                )
            )
        }
    }

    private fun startCountdownTimerWith(totalCountdownTime: Long) {
        countDownTimer = object : CountDownTimer(totalCountdownTime, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                countdownTime = (millisUntilFinished / 1000)
            }

            override fun onFinish() {
                countdownTime = 0L
                notify { listener -> listener.onCountdownTimeOver() }
            }
        }.start()
    }

    fun pauseTimer() = countDownTimer.cancel()

    fun resumeTimer() = startCountdownTimerWith(countdownTime.times(1000L))

    fun checkAnswer(selectedRomanization: String) {
        pauseTimer()

        if (syllabarySymbols.first().romanization == selectedRomanization) {
            notify { listener -> listener.onScoreUpdated(++score) }
            notify { listener -> listener.onCorrectAnswer() }
        } else {
            notify { listener -> listener.onWrongAnswer() }
        }
    }

    fun updateSymbol() {
        syllabarySymbols.removeAt(0)

        notify { listener -> listener.onSymbolUpdated(syllabarySymbols.first()) }

        generateRomanizationOptions()
        notifyRomanizationOptionsGenerated()

        startCountdownTimerWith(totalCountdownTime)

        if (syllabarySymbols.size == 1)
            notify { listener -> listener.onGameFinished() }
    }

    private fun generateRomanizationOptions() {
        val romanizationOptions = flavorBasedSyllabarySymbols
            .generateRomanizationOptions()
            .filterCorrectAnswer()
            .shuffled()

        firstRomanizationOption = romanizationOptions.slice(0..11).random()
        secondRomanizationOption = romanizationOptions.slice(12..23).random()
        thirdRomanizationOption = romanizationOptions.slice(24..35).random()
        fourthRomanizationOption = romanizationOptions.slice(36..44).random()

        setCorrectRomanizationAnswer()
    }

    private fun List<String>.filterCorrectAnswer() : List<String> {
        return this.filterNot { romanization ->
            romanization == syllabarySymbols.first().romanization
        }
    }

    private fun setCorrectRomanizationAnswer() {
        when (Random.nextInt(4)) {
            0 -> firstRomanizationOption = syllabarySymbols.first().romanization
            1 -> secondRomanizationOption = syllabarySymbols.first().romanization
            2 -> thirdRomanizationOption = syllabarySymbols.first().romanization
            3 -> fourthRomanizationOption = syllabarySymbols.first().romanization
        }
    }
}
