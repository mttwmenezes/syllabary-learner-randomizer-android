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

package com.github.mathsemilio.syllabaryrandomizer.common.util

import com.github.mathsemilio.syllabaryrandomizer.BuildConfig
import com.github.mathsemilio.syllabaryrandomizer.common.*
import com.github.mathsemilio.syllabaryrandomizer.others.*
import com.github.mathsemilio.syllabaryrandomizer.domain.model.symbol.SyllabarySymbol

val flavorBasedSyllabarySymbols: List<SyllabarySymbol>
    get() {
        return when (BuildConfig.FLAVOR) {
            FLAVOR_HIRAGANA -> hiraganaSymbolsList
            FLAVOR_KATAKANA -> katakanaSymbolsList
            else -> throw IllegalArgumentException(ILLEGAL_FLAVOR_LITERAL)
        }
    }

fun List<SyllabarySymbol>.generateRomanizationOptions() : List<String> {
    val romanizations = mutableListOf<String>()

    this.forEach { symbol -> romanizations.add(symbol.romanization) }

    return romanizations.toList()
}
