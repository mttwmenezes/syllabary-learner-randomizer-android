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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.container.view

import com.github.mathsemilio.syllabaryrandomizer.ui.common.view.BaseObservableView

abstract class MainActivityView : BaseObservableView<MainActivityView.Listener>() {

    interface Listener {
        fun onToolbarNavigationIconClicked()
    }

    abstract val fragmentContainerId: Int

    abstract fun showToolbar()

    abstract fun hideToolbar()
}
