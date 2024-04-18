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

package com.github.mathsemilio.syllabaryrandomizer.ui.common.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.mathsemilio.syllabaryrandomizer.ui.common.delegate.FragmentContainerDelegate

class FragmentTransactionManager(
    private val fragmentManager: FragmentManager,
    private val fragmentContainerDelegate: FragmentContainerDelegate
) {
    fun replaceFragmentOnContainerWith(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            replace(fragmentContainerDelegate.fragmentContainerId, fragment)
            commitNow()
        }
    }

    fun pushFragmentOntoContainer(fragment: Fragment) {
        fragmentManager.beginTransaction().apply {
            setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            replace(fragmentContainerDelegate.fragmentContainerId, fragment)
            addToBackStack(null)
            commit()
        }
    }

    fun popCurrentFragment() {
        fragmentManager.popBackStackImmediate()
    }
}
