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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.player.SoundEffectsPlayer
import com.github.mathsemilio.syllabaryrandomizer.storage.manager.PreferencesManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.BaseFragment
import com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation.ScreensNavigator
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.welcome.view.GameWelcomeScreenView
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.welcome.view.GameWelcomeScreenViewImpl

class GameWelcomeFragment : BaseFragment(), GameWelcomeScreenView.Listener {

    companion object {
        @JvmStatic
        fun newInstance() = GameWelcomeFragment()
    }

    private lateinit var view: GameWelcomeScreenView

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var soundEffectsPlayer: SoundEffectsPlayer
    private lateinit var screensNavigator: ScreensNavigator

    private var difficulty: GameDifficulty? = null

    private var defaultDifficultyValue: String = SHOW_DIFFICULTY_OPTIONS

    private var isSoundEffectsEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screensNavigator = compositionRoot.screensNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = GameWelcomeScreenViewImpl(inflater, container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferencesManager = compositionRoot.preferencesManager

        defaultDifficultyValue = preferencesManager.defaultDifficultyValue

        setupSoundEffectsPlayer()

        setDifficultyBasedOnDefaultValue()
    }

    private fun setupSoundEffectsPlayer() {
        isSoundEffectsEnabled = preferencesManager.isSoundEffectsEnabled

        if (isSoundEffectsEnabled)
            soundEffectsPlayer = compositionRoot.soundEffectsPlayer
    }

    private fun setDifficultyBasedOnDefaultValue() {
        when (defaultDifficultyValue) {
            "0" -> SHOW_DIFFICULTY_OPTIONS
            "1" -> DEFAULT_DIFFICULTY_BEGINNER
            "2" -> DEFAULT_DIFFICULTY_MEDIUM
            "3" -> DEFAULT_DIFFICULTY_HARD
            else -> throw IllegalArgumentException(ILLEGAL_DEFAULT_DIFFICULTY_VALUE)
        }.also { defaultDifficulty ->
            when (defaultDifficulty) {
                SHOW_DIFFICULTY_OPTIONS -> difficulty = null
                DEFAULT_DIFFICULTY_BEGINNER -> difficulty = GameDifficulty.EASY
                DEFAULT_DIFFICULTY_MEDIUM -> difficulty = GameDifficulty.MEDIUM
                DEFAULT_DIFFICULTY_HARD -> difficulty = GameDifficulty.HARD
            }
        }

        view.bindDifficulty(difficulty)
        view.bindDefaultDifficultyValue(defaultDifficultyValue)
    }

    override fun onSettingsIconClicked() {
        screensNavigator.toSettingsScreen()
    }

    override fun onDifficultyOptionSelected(difficulty: GameDifficulty) {
        this.difficulty = difficulty
    }

    override fun onPlayClickSoundEffect() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playClickSoundEffect()
    }

    override fun onStartButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        screensNavigator.toMainScreen(difficulty ?: throw RuntimeException(NULL_DIFFICULTY))
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
    }
}
