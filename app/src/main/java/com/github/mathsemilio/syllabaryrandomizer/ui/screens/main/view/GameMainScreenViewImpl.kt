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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.main.view

import android.animation.ValueAnimator
import android.view.*
import android.widget.*
import com.google.android.material.chip.*
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty

class GameMainScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : GameMainScreenView() {

    private lateinit var textViewGameDifficulty: TextView
    private lateinit var textViewCurrentScore: TextView
    private lateinit var progressBarGameTimer: ProgressBar

    private lateinit var textViewCurrentSymbol: TextView
    private lateinit var chipGroupRomanizationOptions: ChipGroup

    private lateinit var chipButtonFirstOption: Chip
    private lateinit var chipButtonSecondOption: Chip
    private lateinit var chipButtonThirdOption: Chip
    private lateinit var chipButtonFourthOption: Chip
    private lateinit var fabExit: FloatingActionButton

    private lateinit var fabPause: FloatingActionButton
    private lateinit var fabCheckAnswer: FloatingActionButton

    private lateinit var valueAnimator: ValueAnimator

    private lateinit var difficulty: GameDifficulty

    private lateinit var selectedRomanization: String

    init {
        rootView = layoutInflater.inflate(R.layout.game_main_screen, container, false)

        initializeViews()

        setRomanizationOptionsOnCheckedChangedListener()

        attachExitButtonOnClickListener()
        attachPauseButtonOnClickListener()
        attachCheckAnswerButtonOnCLickListener()
    }

    private fun initializeViews() {
        textViewGameDifficulty = findViewById(R.id.text_view_game_difficulty)
        textViewCurrentScore = findViewById(R.id.text_view_current_score)

        progressBarGameTimer = findViewById(R.id.progress_bar_game_timer)
        textViewCurrentSymbol = findViewById(R.id.text_view_current_symbol)

        chipGroupRomanizationOptions = findViewById(R.id.chip_group_romanization_options)
        chipButtonFirstOption = findViewById(R.id.chip_button_first_option)
        chipButtonSecondOption = findViewById(R.id.chip_button_second_option)
        chipButtonThirdOption = findViewById(R.id.chip_button_third_option)
        chipButtonFourthOption = findViewById(R.id.chip_button_fourth_option)

        fabExit = findViewById(R.id.fab_exit)
        fabPause = findViewById(R.id.fab_pause)
        fabCheckAnswer = findViewById(R.id.fab_check_answer)
    }

    private fun setRomanizationOptionsOnCheckedChangedListener() {
        chipGroupRomanizationOptions.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) {
                fabCheckAnswer.isEnabled = false
            } else {
                fabCheckAnswer.isEnabled = true

                notifyPlayClickSoundEffect()

                val checkedChip = group.findViewById<Chip>(checkedId)
                selectedRomanization = checkedChip.text.toString()

                notify { listener -> listener.onRomanizationOptionSelected(selectedRomanization) }
            }
        }
    }

    private fun notifyPlayClickSoundEffect() {
        notify { listener -> listener.onPlayClickSoundEffect() }
    }

    private fun attachExitButtonOnClickListener() {
        fabExit.setOnClickListener {
            notify { listener -> listener.onExitButtonClicked() }
        }
    }

    private fun attachPauseButtonOnClickListener() {
        fabPause.setOnClickListener {
            notify { listener -> listener.onPauseButtonClicked() }
        }
    }

    private fun attachCheckAnswerButtonOnCLickListener() {
        fabCheckAnswer.setOnClickListener {
            notify { listener -> listener.onCheckAnswerButtonClicked() }
        }
    }

    override fun bind(difficulty: GameDifficulty) {
        this.difficulty = difficulty
        setupGameDifficultyTextView()
        setupCountdownTimer()
    }

    private fun setupGameDifficultyTextView() {
        textViewGameDifficulty.text = when (difficulty.value) {
            GameDifficulty.EASY.value -> getString(R.string.difficulty_beginner)
            GameDifficulty.MEDIUM.value -> getString(R.string.difficulty_medium)
            GameDifficulty.HARD.value -> getString(R.string.difficulty_hard)
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }
    }

    private fun setupCountdownTimer() {
        progressBarGameTimer.max = when (difficulty.value) {
            GameDifficulty.EASY.value -> PROGRESS_BAR_MAX_VALUE_BEGINNER
            GameDifficulty.MEDIUM.value -> PROGRESS_BAR_MAX_VALUE_MEDIUM
            GameDifficulty.HARD.value -> PROGRESS_BAR_MAX_VALUE_HARD
            else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
        }

        setupCountdownTimerAnimation()
    }

    private fun setupCountdownTimerAnimation() {
        valueAnimator = ValueAnimator.ofInt(progressBarGameTimer.max, 0)

        valueAnimator.duration = difficulty.countdownTime
        valueAnimator.addUpdateListener { animator ->
            progressBarGameTimer.progress = animator.animatedValue as Int
        }

        valueAnimator.start()
    }

    override fun updateScore(score: Int) {
        textViewCurrentScore.text = context.getString(R.string.score, score)
    }

    override fun pauseCountdownTimer() {
        valueAnimator.pause()
    }

    override fun resumeCountdownTimer() {
        valueAnimator.resume()
    }

    override fun resetCountdownTimer() {
        valueAnimator.cancel()
        valueAnimator.start()
    }

    override fun updateSymbol(symbol: String) {
        textViewCurrentSymbol.text = symbol
    }

    override fun updateRomanizationOptions(options: List<String>) {
        chipButtonFirstOption.text = options[0]
        chipButtonSecondOption.text = options[1]
        chipButtonThirdOption.text = options[2]
        chipButtonFourthOption.text = options[3]
    }

    override fun clearCheckedRomanization() {
        chipGroupRomanizationOptions.clearCheck()
    }
}
