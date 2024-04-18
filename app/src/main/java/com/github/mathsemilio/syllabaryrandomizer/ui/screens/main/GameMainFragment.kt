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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.github.mathsemilio.syllabaryrandomizer.common.eventbus.EventListener
import com.github.mathsemilio.syllabaryrandomizer.common.eventbus.EventSubscriber
import com.github.mathsemilio.syllabaryrandomizer.domain.eventchannel.GameEventChannel
import com.github.mathsemilio.syllabaryrandomizer.domain.eventchannel.GameEventChannelListener
import com.github.mathsemilio.syllabaryrandomizer.domain.model.game.GameDifficulty
import com.github.mathsemilio.syllabaryrandomizer.domain.model.symbol.SyllabarySymbol
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.player.SoundEffectsPlayer
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.syllabary.SyllabarySoundsPlayer
import com.github.mathsemilio.syllabaryrandomizer.storage.manager.PreferencesManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.BaseFragment
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.DialogManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation.ScreensNavigator
import com.github.mathsemilio.syllabaryrandomizer.ui.common.provider.BackPressedCallbackProvider
import com.github.mathsemilio.syllabaryrandomizer.ui.dialog.prompt.PromptDialogEvent
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.main.MainScreenState.*
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.main.view.GameMainScreenView
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.main.view.GameMainScreenViewImpl

class GameMainFragment : BaseFragment(),
    GameMainScreenView.Listener,
    GameEventChannelListener,
    EventListener {

    companion object {
        @JvmStatic
        fun with(difficulty: GameDifficulty) = GameMainFragment().apply {
            arguments = Bundle(1).apply {
                putSerializable(ARG_DIFFICULTY, difficulty)
            }
        }
    }

    private lateinit var view: GameMainScreenView

    private lateinit var eventChannel: GameEventChannel

    private lateinit var backPressedCallbackProvider: BackPressedCallbackProvider
    private lateinit var syllabarySoundsPlayer: SyllabarySoundsPlayer
    private lateinit var soundEffectsPlayer: SoundEffectsPlayer
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var dialogManager: DialogManager

    private lateinit var eventSubscriber: EventSubscriber

    private lateinit var difficulty: GameDifficulty
    private lateinit var selectedRomanization: String

    private var isSyllabarySoundsEnabled = true
    private var isSoundEffectsEnabled = true

    private var screenState = TIMER_RUNNING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventChannel = compositionRoot.gameEventChannel

        backPressedCallbackProvider = compositionRoot.backPressedCallbackProvider
        preferencesManager = compositionRoot.preferencesManager
        screensNavigator = compositionRoot.screensNavigator
        dialogManager = compositionRoot.dialogManager

        eventSubscriber = compositionRoot.eventSubscriber

        setupSoundEffectsPlayer()
        setupSyllabarySoundsPlayer()
    }

    private fun setupSoundEffectsPlayer() {
        isSoundEffectsEnabled = preferencesManager.isSoundEffectsEnabled

        if (isSoundEffectsEnabled)
            soundEffectsPlayer = compositionRoot.soundEffectsPlayer
    }

    private fun setupSyllabarySoundsPlayer() {
        isSyllabarySoundsEnabled = preferencesManager.isSyllabarySoundsEnabled

        if (isSyllabarySoundsEnabled)
            syllabarySoundsPlayer = compositionRoot.syllabarySoundsPlayer
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = GameMainScreenViewImpl(inflater, container)
        return view.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        difficulty = requireArguments().getSerializable(ARG_DIFFICULTY) as GameDifficulty

        eventChannel.addListener(this)
        eventChannel.startGameOn(difficulty)

        this.view.bind(difficulty)

        setupOnBackPressedDispatcher()
    }

    private fun setupOnBackPressedDispatcher() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressedCallbackProvider.getOnBackPressedCallback { onExitButtonClicked() }
        )
    }

    private fun getNextSymbol() {
        if (eventChannel.isGameFinished) {
            if (eventChannel.currentScore == PERFECT_SCORE)
                preferencesManager.incrementPerfectScoresAchieved()

            screensNavigator.toResultScreen(difficulty, eventChannel.currentScore)
        } else {
            eventChannel.updateSymbol()
        }
    }

    override fun onRomanizationOptionSelected(selectedRomanization: String) {
        this.selectedRomanization = selectedRomanization

        if (isSyllabarySoundsEnabled)
            syllabarySoundsPlayer.playFor(selectedRomanization)
    }

    override fun onExitButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        pauseTimer()
        screenState = DIALOG_BEING_SHOWN
        dialogManager.showExitGameDialog()
    }

    private fun pauseTimer() {
        view.pauseCountdownTimer()
        eventChannel.pauseTimer()
    }

    override fun onPauseButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        pauseTimer()
        screenState = DIALOG_BEING_SHOWN
        dialogManager.showGamePausedDialog()
    }

    override fun onCheckAnswerButtonClicked() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playButtonClickSoundEffect()

        view.pauseCountdownTimer()
        screenState = DIALOG_BEING_SHOWN
        eventChannel.checkAnswer(selectedRomanization)
    }

    override fun onPlayClickSoundEffect() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playClickSoundEffect()
    }

    override fun onScoreUpdated(score: Int) {
        view.updateScore(score)
    }

    override fun onRomanizationOptionsUpdated(options: List<String>) {
        view.updateRomanizationOptions(options)
    }

    override fun onSymbolUpdated(symbol: SyllabarySymbol) {
        view.updateSymbol(symbol.symbol)
    }

    override fun onCorrectAnswer() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playSuccessSoundEffect()

        screenState = DIALOG_BEING_SHOWN
        dialogManager.showCorrectAnswerDialog()
    }

    override fun onWrongAnswer() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playErrorSoundEffect()

        screenState = DIALOG_BEING_SHOWN
        dialogManager.showWrongAnswerDialog(eventChannel.currentSymbol.romanization)
    }

    override fun onCountdownTimeOver() {
        if (isSoundEffectsEnabled)
            soundEffectsPlayer.playErrorSoundEffect()

        screenState = DIALOG_BEING_SHOWN
        dialogManager.showTimeOverDialog(eventChannel.currentSymbol.romanization)
    }

    override fun onEvent(event: Any) {
        when (event) {
            is PromptDialogEvent -> handlePromptDialogEvent(event)
        }
    }

    private fun handlePromptDialogEvent(event: PromptDialogEvent) {
        when (event) {
            PromptDialogEvent.PositiveButtonClicked ->
                handlePromptDialogPositiveButtonClickBasedOn(dialogManager.dialogTag)
            PromptDialogEvent.NegativeButtonClicked ->
                handlePromptDialogNegativeButtonClickBasedOn(dialogManager.dialogTag)
        }
    }

    private fun handlePromptDialogPositiveButtonClickBasedOn(tag: String) {
        when (tag) {
            TAG_CORRECT_ANSWER_DIALOG -> updateSymbol()
            TAG_WRONG_ANSWER_DIALOG -> updateSymbol()
            TAG_TIME_OVER_DIALOG -> updateSymbol()
            TAG_EXIT_GAME_DIALOG -> resumeTimer()
            TAG_GAME_PAUSED_DIALOG -> resumeTimer()
        }
    }

    private fun updateSymbol() {
        getNextSymbol()
        view.resetCountdownTimer()
        view.clearCheckedRomanization()
        screenState = TIMER_RUNNING
    }

    private fun resumeTimer() {
        view.resumeCountdownTimer()
        eventChannel.resumeTimer()
        screenState = TIMER_RUNNING
    }

    private fun handlePromptDialogNegativeButtonClickBasedOn(tag: String) {
        when (tag) {
            TAG_EXIT_GAME_DIALOG -> screensNavigator.toWelcomeScreen()
        }
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        eventSubscriber.subscribe(this)
    }

    override fun onResume() {
        super.onResume()

        if (screenState == TIMER_PAUSED)
            resumeTimer()
    }

    override fun onPause() {
        super.onPause()

        pauseTimer()

        if (screenState == TIMER_RUNNING)
            screenState = TIMER_PAUSED
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        eventSubscriber.unsubscribe(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        eventChannel.removeListener(this)

        if (isSyllabarySoundsEnabled)
            syllabarySoundsPlayer.onClearInstance()
    }
}
