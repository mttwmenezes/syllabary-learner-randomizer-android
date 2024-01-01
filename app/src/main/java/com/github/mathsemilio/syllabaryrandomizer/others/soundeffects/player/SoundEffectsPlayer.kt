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

package com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.player

import android.content.Context
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.manager.SoundEffectsManager
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.model.SoundEffect
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.player.SoundEffects.*

class SoundEffectsPlayer(context: Context) : SoundEffectsManager() {

    private val soundEffects = HashSet<SoundEffect>(
        listOf(
            SoundEffect(BUTTON_CLICK.name, R.raw.jaoreir_button_simple_01),
            SoundEffect(CLICK.name, R.raw.brandondelehoy_series_of_clicks),
            SoundEffect(SUCCESS.name, R.raw.mativve_electro_success_sound),
            SoundEffect(ERROR.name, R.raw.autistic_lucario_error),
        )
    )

    init {
        setContext(context)
        allocate(soundEffects)
    }

    fun playButtonClickSoundEffect() = play(BUTTON_CLICK.name)

    fun playClickSoundEffect() = play(CLICK.name)

    fun playSuccessSoundEffect() = play(SUCCESS.name)

    fun playErrorSoundEffect() = play(ERROR.name)
}
