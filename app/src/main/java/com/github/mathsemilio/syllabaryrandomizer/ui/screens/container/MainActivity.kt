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

package com.github.mathsemilio.syllabaryrandomizer.ui.screens.container

import android.os.Bundle
import com.github.mathsemilio.syllabaryrandomizer.R
import com.github.mathsemilio.syllabaryrandomizer.ui.common.BaseActivity
import com.github.mathsemilio.syllabaryrandomizer.ui.common.delegate.FragmentContainerDelegate
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.AppThemeManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.manager.ToolbarVisibilityManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation.ScreensNavigator
import com.github.mathsemilio.syllabaryrandomizer.ui.common.permission.PermissionHandler
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.container.view.MainActivityView
import com.github.mathsemilio.syllabaryrandomizer.ui.screens.container.view.MainActivityViewImpl

class MainActivity : BaseActivity(),
    MainActivityView.Listener,
    FragmentContainerDelegate,
    ToolbarVisibilityManager.Listener {

    private lateinit var view: MainActivityView

    private lateinit var toolbarVisibilityManager: ToolbarVisibilityManager
    private lateinit var permissionHandler: PermissionHandler
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var appThemeManager: AppThemeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        view = MainActivityViewImpl(layoutInflater, parent = null)

        permissionHandler = compositionRoot.permissionHandler
        toolbarVisibilityManager = compositionRoot.toolbarVisibilityManager
        screensNavigator = compositionRoot.screensNavigator
        appThemeManager = compositionRoot.appThemeManager

        setTheme(R.style.Theme_SyllabaryRandomizer)

        appThemeManager.setThemeFromPreference()

        setContentView(view.rootView)

        if (savedInstanceState == null)
            screensNavigator.toWelcomeScreen()
    }

    override val fragmentContainerId
        get() = view.fragmentContainerId

    override fun onToolbarNavigationIconClicked() = screensNavigator.navigateUp()

    override fun onShowToolbar() = view.showToolbar()

    override fun onHideToolbar() = view.hideToolbar()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHandler.onRequestPermissionResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()
        view.addListener(this)
        toolbarVisibilityManager.addListener(this)
    }

    override fun onStop() {
        super.onStop()
        view.removeListener(this)
        toolbarVisibilityManager.removeListener(this)
    }
}
