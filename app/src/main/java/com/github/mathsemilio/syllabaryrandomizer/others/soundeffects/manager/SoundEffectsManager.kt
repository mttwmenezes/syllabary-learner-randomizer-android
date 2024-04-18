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

package com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.manager

import android.content.Context
import android.media.*
import com.github.mathsemilio.syllabaryrandomizer.common.util.playSFX
import com.github.mathsemilio.syllabaryrandomizer.others.soundeffects.model.SoundEffect

abstract class SoundEffectsManager {

    companion object {
        private const val DEFAULT_VOLUME = 1.0f
        private const val MAX_AUDIO_STREAMS = 2
    }

    private lateinit var context: Context

    private val allocatedSoundEffects = HashMap<Int, SoundEffect>()

    private val audioAttributes = AudioAttributes.Builder()
        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
        .setUsage(AudioAttributes.USAGE_GAME)
        .build()

    private val soundPool = SoundPool.Builder()
        .setAudioAttributes(audioAttributes)
        .setMaxStreams(MAX_AUDIO_STREAMS)
        .build()

    protected fun setContext(context: Context) {
        this.context = context
    }

    protected fun allocate(soundEffects: HashSet<SoundEffect>) {
        soundEffects.forEach { soundEffect ->
            val soundEffectId = soundPool.load(context, soundEffect.rawResourceId, 1)
            allocatedSoundEffects[soundEffectId] = soundEffect
        }
    }

    protected fun play(soundEffectName: String) {
        soundPool.playSFX(findIdFrom(soundEffectName), DEFAULT_VOLUME)
    }

    private fun findIdFrom(soundEffectName: String): Int {
        var soundEffectId = 0

        allocatedSoundEffects.forEach { entry ->
            if (soundEffectName == entry.value.name)
                soundEffectId = entry.key
        }

        return soundEffectId
    }

    fun onClearInstance() {
        soundPool.release()
        allocatedSoundEffects.clear()
    }
}
