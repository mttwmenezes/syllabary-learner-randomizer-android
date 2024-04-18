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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.result.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.ILLEGAL_GAME_DIFFICULTY_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.PERFECT_SCORE
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty

class GameResultScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : GameResultScreenView() {

    private lateinit var textViewYouGotSymbolsCorrectly: TextView
    private lateinit var textViewGameDifficultyValue: TextView
    private lateinit var textViewPerfectScoresAchieved: TextView

    private lateinit var fabHome: FloatingActionButton
    private lateinit var fabPlayAgain: FloatingActionButton
    private lateinit var fabShare: FloatingActionButton

    private lateinit var difficulty: GameDifficulty

    init {
        rootView = layoutInflater.inflate(R.layout.game_result_screen, container, false)

        initializeViews()

        attachHomeButtonOnClickListener()
        attachPlayAgainButtonOnCLickListener()
        attachShareButtonOnCLickListener()
    }

    private fun initializeViews() {
        textViewYouGotSymbolsCorrectly = findViewById(R.id.text_view_you_got_correctly)
        textViewGameDifficultyValue = findViewById(R.id.text_view_game_difficulty_value)
        textViewPerfectScoresAchieved = findViewById(R.id.text_view_perfect_scores_achieved)

        fabHome = findViewById(R.id.fab_home)
        fabPlayAgain = findViewById(R.id.fab_play_again)
        fabShare = findViewById(R.id.fab_share)
    }

    private fun attachHomeButtonOnClickListener() {
        fabHome.setOnClickListener {
            notify { listener ->
                listener.onHomeButtonClicked()
            }
        }
    }

    private fun attachPlayAgainButtonOnCLickListener() {
        fabPlayAgain.setOnClickListener {
            notify { listener ->
                listener.onPlayAgainButtonClicked()
            }
        }
    }

    private fun attachShareButtonOnCLickListener() {
        fabShare.setOnClickListener {
            notify { listener ->
                listener.onShareScoreButtonClicked()
            }
        }
    }

    override fun bindFinalScore(finalScore: Int) {
        if (finalScore == 0)
            fabShare.isVisible = false

        setupYouGotSymbolsCorrectlyTextView(finalScore)
    }

    override fun bindDifficulty(difficulty: GameDifficulty) {
        this.difficulty = difficulty
        setupGameDifficultyTextView(difficulty.value)
    }

    override fun bindPerfectScores(perfectScores: Int) {
        setupPerfectScoresAchievedTextView(perfectScores)
    }

    private fun setupYouGotSymbolsCorrectlyTextView(score: Int) {
        textViewYouGotSymbolsCorrectly.text = when (score) {
            1 -> context.getString(R.string.you_got_one_symbol_correctly, score)
            PERFECT_SCORE -> context.getString(R.string.you_got_all_symbols_correctly)
            else -> context.getString(R.string.you_got_symbols_correctly, score)
        }
    }

    private fun setupGameDifficultyTextView(difficultyValue: Int) {
        textViewGameDifficultyValue.text = when (difficultyValue) {
            GameDifficulty.EASY.value -> getString(R.string.difficulty_beginner)
            GameDifficulty.MEDIUM.value -> getString(R.string.difficulty_medium)
            GameDifficulty.HARD.value -> getString(R.string.difficulty_hard)
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }
    }

    private fun setupPerfectScoresAchievedTextView(perfectScores: Int) {
        textViewPerfectScoresAchieved.text = perfectScores.toString()
    }
}
