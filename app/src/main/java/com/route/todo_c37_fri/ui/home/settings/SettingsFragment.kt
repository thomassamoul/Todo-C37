package com.route.todo_c37_fri.ui.home.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.route.todo_c37_fri.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    lateinit var viewBinding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentSettingsBinding.inflate(
            inflater, container, false
        )
        return viewBinding.root
    }
}