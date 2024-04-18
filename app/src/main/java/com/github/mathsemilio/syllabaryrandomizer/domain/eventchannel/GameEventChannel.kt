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

package com.github.mathsemilio.syllabaryrandomizer.domain.eventchannel

import com.github.mathsemilio.syllabaryrandomizer.common.observable.BaseObservable
import com.github.mathsemilio.syllabaryrandomizer.domain.backend.GameBackend
import com.github.mathsemilio.syllabaryrandomizer.domain.backend.GameBackendListener
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty
import com.github.mathsemilio.syllabaryrandomizer.domain.model.symbol.SyllabarySymbol

class GameEventChannel(
    private val gameBackend: GameBackend
) : BaseObservable<GameEventChannelListener>(),
    GameBackendListener {

    var currentScore = 0

    var isGameFinished = false

    lateinit var currentSymbol: SyllabarySymbol

    init {
        gameBackend.addListener(this)
    }

    fun startGameOn(difficulty: GameDifficulty) = gameBackend.startGameOn(difficulty)

    fun checkAnswer(selectedRomanization: String) = gameBackend.checkAnswer(selectedRomanization)

    fun updateSymbol() = gameBackend.updateSymbol()

    fun pauseTimer() = gameBackend.pauseTimer()

    fun resumeTimer() = gameBackend.resumeTimer()

    override fun onSymbolUpdated(symbol: SyllabarySymbol) {
        currentSymbol = symbol

        notify { listener ->
            listener.onSymbolUpdated(symbol)
        }
    }

    override fun onScoreUpdated(score: Int) {
        currentScore = score

        notify { listener ->
            listener.onScoreUpdated(score)
        }
    }

    override fun onRomanizationOptionsUpdated(options: List<String>) {
        notify { listener ->
            listener.onRomanizationOptionsUpdated(options)
        }
    }

    override fun onCorrectAnswer() {
        notify { listener ->
            listener.onCorrectAnswer()
        }
    }

    override fun onWrongAnswer() {
        notify { listener ->
            listener.onWrongAnswer()
        }
    }

    override fun onCountdownTimeOver() {
        notify { listener ->
            listener.onCountdownTimeOver()
        }
    }

    override fun onGameFinished() {
        isGameFinished = true
    }
}
