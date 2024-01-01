# Syllabary Learner Randomizer
A simple, yet effective, Japanese Syllabary randomization application for Android.

## Objective
This app aims to help learners of the Japanese language quickly make associations with the basic
Japanese syllabary (Hiragana and Katakana) symbols and their Hepburn romanizations.

<i>For more info on both the Hiragana and Katakana, please refer to these links:</i>
<p><a href="https://en.wikipedia.org/wiki/Hiragana">Wikipedia - Hiragana</a></p>
<p><a href="https://en.wikipedia.org/wiki/Katakana">Wikipedia - Katakana</a></p>

### How it works?
Before the app starts, the user selects a difficulty (which can also be preselected through settings).
The app's algorithm then generates a random order of all the 46 syllabary symbols.
The user's objective is to select a romanization for the symbol currently being displayed on
the screen before the time runs out. The amount of time available for the user depends on the difficulty he selected.
Once the user goes through all the symbols, he will be directed to a result screen,
where the final score is presented along with the option to play the game again or share his score.

### Application Screenshots
<p>To checkout screenshots for each flavor, please visit these galleries:</p>
<p><a href="https://postimg.cc/gallery/15gGNgg">Hiragana Flavor Gallery</a></p>
<p><a href="https://postimg.cc/gallery/w6mvNq4">Katakana Flavor Gallery</a></p>

## Project organization
<p>This project uses <i>Product Flavors</i> to implement the basic functionality of the app in the "main" 
flavor, and allow secondary flavors to customize it. The main flavor implements the Hiragana 
syllabary (the project was originally Hiragana only), and the Katakana flavor implements the Katakana syllabary.</p>

**Important:**
On the build.gradle file of the app module (the only module in the app), there are two flavors declared: Hiragana and Katakana.
As explained earlier, the main flavor already implements the Hiragana syllabary. This was done for two reasons mainly:
1. To keep the project's original organization;
2. To make the build process easier: When you need to build a version for a particular syllabary, the options are explicit.

### Build instructions
To build a particular flavor of the app inside Android Studio, you just need to change the current build variant. To do this, go to Build > Select Build Variant... and select either HiraganaDebug or KatakanaDebug.

## Development Technologies
3rd party libraries used:

1. <a href="https://github.com/material-components/material-components-android">Material Design Components</a>;
2. <a href="https://developer.android.com/guide/topics/ui/settings">Jetpack Preference</a>;
3. <a href="https://developer.android.com/topic/libraries/architecture/workmanager/basics">WorkManager</a>;
4. <a href="https://github.com/airbnb/lottie-android">Lottie Android</a>;
5. <a href="https://developers.google.com/admob/android/sdk">Google Mobile Ads SDK</a>;

## License
```
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
```
