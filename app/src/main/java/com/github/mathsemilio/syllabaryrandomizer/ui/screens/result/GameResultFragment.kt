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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.result

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.common.ARG_DIFFICULTY
import com.github.mathsemilio.syllabaryrandomizer.common.ARG_SCORE
import com.github.mathsemilio.syllabaryrandomizer.common.PERFECT_SCORE
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.player.SoundEffectsPlayer
import com.github.mathsemilio.syllabaryrandomizer.ui.common.provider.BackPressedCallbackProvider
import com.github.mathsemilio.syllabaryrandomizer.storage.manager.PreferencesManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.BaseFragment
import com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation.ScreensNavigator
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.result.view.GameResultScreenView
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.result.view.GameResultScreenViewImpl

class GameResultFragment : BaseFragment(), GameResultScreenView.Listener {

    companion object {
        @JvmStatic
        fun with(difficulty: GameDifficulty, finalScore: Int) = GameResultFragment().apply {
            arguments = Bundle(2).apply {
                putSerializable(ARG_DIFFICULTY, difficulty)
                putInt(ARG_SCORE, finalScore)
            }
        }
    }

    private lateinit var view: GameResultScreenView

    private lateinit var backPressedCallbackProvider: BackPressedCallbackProvider
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var soundEffectsPlayer: SoundEffectsPlayer
    private lateinit var screensNavigator: ScreensNavigator

    private lateinit var difficulty: GameDifficulty
    private var finalScore = 0

    private var isSoundEffectsEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backPressedCallbackProvider = compositionRoot.backPressedCallbackProvider
        preferencesManager = compositionRoot.preferencesManager
        screensNavigator = compositionRoot.screensNavigator

        setupSoundEffectsPlayer()
    }

    private fun setupSoundEffectsPlayer() {
        isSoundEffectsEnabled = preferencesManager.isSoundEffectsEnabled

        if (isSoundEffectsEnabled)
            soundEffectsPlayer = compositionRoot.soundEffectsPlayer
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = GameResultScreenViewImpl(inflater, container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOnBackPressedDispatcher()

        difficulty = requireArguments().getSerializable(ARG_DIFFICULTY) as GameDifficulty
        finalScore = requireArguments().getInt(ARG_SCORE, 0)
    }

    private fun setupOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallbackProvider.getOnBackPressedCallback {
                screensNavigator.toWelcomeScreen()
            }
        )
    }

    override fun onHomeButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        screensNavigator.toWelcomeScreen()
    }

    override fun onPlayAgainButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        screensNavigator.toMainScreen(difficulty)
    }

    override fun onShareScoreButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        shareScore(finalScore)
    }

    private fun shareScore(score: Int) {
        startActivity(
            Intent.createChooser(
                getShareScoreIntent(score),
                getString(R.string.share_score_panel_title)
            )
        )
    }

    private fun getShareScoreIntent(score: Int): Intent {
        return Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getShareScoreMessage(score))
            type = "text/plain"
        }
    }

    private fun getShareScoreMessage(score: Int): String {
        return if (score == PERFECT_SCORE)
            getString(R.string.share_perfect_final_score)
        else
            getString(R.string.share_final_score, score)
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        view.bindFinalScore(finalScore)
        view.bindDifficulty(difficulty)
        view.bindPerfectScores(preferencesManager.perfectScoresAchieved)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
    }
}
