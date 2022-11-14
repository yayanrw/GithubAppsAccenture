package com.heyproject.githubapps.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.heyproject.githubapps.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {
    private val viewModel: SettingViewModel by viewModels()

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBar()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            settingFragment = this@SettingFragment
        }

        setObserver()
        setListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListener() {
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            viewModel.saveThemeSettings(isChecked)
        }
    }

    private fun setObserver() {
        viewModel.fetchThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            binding.switchTheme.isChecked = isDarkModeActive
        }
    }

    fun goToPickLanguage() {
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun setActionBar() {
        activity?.actionBar?.apply {
            elevation = 0.0F
        }
    }
}