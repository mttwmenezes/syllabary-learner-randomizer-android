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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.welcome.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.ILLEGAL_GAME_DIFFICULTY_VALUE
import com.github.mathsemilio.syllabaryrandomizer.common.SHOW_DIFFICULTY_OPTIONS
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty

class GameWelcomeScreenViewImpl(
    layoutInflater: LayoutInflater,
    container: ViewGroup?
) : GameWelcomeScreenView() {

    private lateinit var imageViewSettingsIcon: ImageView

    private lateinit var textViewOnGameDifficulty: TextView

    private lateinit var chipGroupGameDifficulty: ChipGroup
    private lateinit var chipButtonBeginner: Chip
    private lateinit var chipButtonMedium: Chip
    private lateinit var chipButtonHard: Chip

    private lateinit var buttonStart: MaterialButton

    private lateinit var difficulty: GameDifficulty

    private var defaultDifficultyValue: String = SHOW_DIFFICULTY_OPTIONS

    init {
        rootView = layoutInflater.inflate(R.layout.game_welcome_screen, container, false)

        initializeViews()

        attachSettingsIconOnClickListener()

        attachStartButtonOnClickListener()
    }

    private fun initializeViews() {
        imageViewSettingsIcon = findViewById(R.id.image_view_settings_icon)

        textViewOnGameDifficulty = findViewById(R.id.text_view_on_game_difficulty)

        chipGroupGameDifficulty = findViewById(R.id.chip_group_game_difficulty)
        chipButtonBeginner = findViewById(R.id.chip_button_beginner)
        chipButtonMedium = findViewById(R.id.chip_button_medium)
        chipButtonHard = findViewById(R.id.chip_button_hard)

        buttonStart = findViewById(R.id.button_start)
    }

    private fun attachSettingsIconOnClickListener() {
        imageViewSettingsIcon.setOnClickListener {
            notify { listener ->
                listener.onSettingsIconClicked()
            }
        }
    }

    private fun attachStartButtonOnClickListener() {
        buttonStart.setOnClickListener {
            notify { listener ->
                listener.onStartButtonClicked()
            }
        }
    }

    override fun bindDifficulty(difficulty: GameDifficulty?) {
        if (difficulty != null)
            this.difficulty = difficulty
    }

    override fun bindDefaultDifficultyValue(defaultDifficultyValue: String) {
        this.defaultDifficultyValue = defaultDifficultyValue
        setupDefaultDifficultyOptions()
    }

    private fun setupDefaultDifficultyOptions() {
        if (defaultDifficultyValue == SHOW_DIFFICULTY_OPTIONS)
            setDifficultyOptionsOnCheckedChangedListener()
        else
            setupUIForDifficultyPreSelected()
    }

    private fun setDifficultyOptionsOnCheckedChangedListener() {
        chipGroupGameDifficulty.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) {
                buttonStart.isEnabled = false
            } else {
                buttonStart.isEnabled = true

                notifyPlayClickSoundEffect()

                val checkedChip = group.findViewById<Chip>(checkedId)
                determineDifficultyFrom(checkedChip.text.toString())
            }
        }
    }

    private fun notifyPlayClickSoundEffect() {
        notify { listener ->
            listener.onPlayClickSoundEffect()
        }
    }

    private fun setupUIForDifficultyPreSelected() {
        showOnDifficultyTextView()

        chipGroupGameDifficulty.isVisible = false
        buttonStart.isEnabled = true
    }

    private fun showOnDifficultyTextView() {
        textViewOnGameDifficulty.isVisible = true
        textViewOnGameDifficulty.text = getDifficultyText()
    }

    private fun getDifficultyText(): String {
        return context.getString(
            R.string.default_difficulty_selected,
            when (difficulty.value) {
                GameDifficulty.EASY.value -> context.getString(R.string.difficulty_beginner)
                GameDifficulty.MEDIUM.value -> context.getString(R.string.difficulty_medium)
                GameDifficulty.HARD.value -> context.getString(R.string.difficulty_hard)
                else -> throw IllegalArgumentException(ILLEGAL_GAME_DIFFICULTY_VALUE)
            }
        )
    }

    private fun determineDifficultyFrom(difficultyOption: String) {
        val difficulty = when (difficultyOption) {
            context.getString(R.string.difficulty_beginner) -> GameDifficulty.EASY
            context.getString(R.string.difficulty_medium) -> GameDifficulty.MEDIUM
            else -> GameDifficulty.HARD
        }

        notifyDifficultyOptionSelected(difficulty)
    }

    private fun notifyDifficultyOptionSelected(difficulty: GameDifficulty) {
        notify { listener ->
            listener.onDifficultyOptionSelected(difficulty)
        }
    }
}
