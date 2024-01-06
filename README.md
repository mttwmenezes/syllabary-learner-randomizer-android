# Syllabary Learner Randomizer
Japanese syllabary quiz application for Android.

## Features
- Difficulty selection
- Training reminders
- Perfect scores counter
- In-quiz syllabary sounds

## Screenshots
<img src="https://i.postimg.cc/15hF14Hr/hiragana-welcome.png" alt="hiragana-welcome" width="200"/>
<img src="https://i.postimg.cc/3wZMYtzF/hiragana-inquiz.png" alt="hiragana-inquiz" width="200"/>
<img src="https://i.postimg.cc/G26y2p1r/hiragana-result.png" alt="hiragana-result" width="200"/>

## Project organization
This projects uses *Product flavors*. There are two of them: Hiragana and Katakana. The code in the main
module implements the core app (Hiragana syllabary). To build the Katakana-flavored app, go to Build > Select Build Variant...
and choose `katakanaDebug`.

## Development technologies
Libraries/Frameworks used:
- <a href="https://developer.android.com/guide/topics/ui/settings">AndroidX Preference</a>
- <a href="https://developer.android.com/topic/libraries/architecture/workmanager/basics">WorkManager</a>
- <a href="https://github.com/airbnb/lottie-android">Lottie</a>

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
