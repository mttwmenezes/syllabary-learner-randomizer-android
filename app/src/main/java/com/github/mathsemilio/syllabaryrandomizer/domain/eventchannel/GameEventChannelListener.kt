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

import com.github.mathsemilio.syllabaryrandomizer.domain.model.symbol.SyllabarySymbol

interface GameEventChannelListener {

    fun onScoreUpdated(score: Int)

    fun onRomanizationOptionsUpdated(options: List<String>)

    fun onSymbolUpdated(symbol: SyllabarySymbol)

    fun onCorrectAnswer()

    fun onWrongAnswer()

    fun onCountdownTimeOver()
}
