package com.heyproject.githubapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.heyproject.githubapps.databinding.FragmentHomeBinding
import com.heyproject.githubapps.presentation.adapter.LoadingStateAdapter
import com.heyproject.githubapps.presentation.adapter.UserAdapter
import com.heyproject.githubapps.presentation.setting.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val settingViewModel: SettingViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            homeFragment = this@HomeFragment
            rvGithubUsers.apply {
                adapter = UserAdapter()
                setHasFixedSize(true)
            }
        }

        fetchUsers()
        setObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setObserver() {
        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive ->
            settingViewModel.saveThemeSettings(isDarkModeActive)
        }
    }

    private fun fetchUsers() {
        userAdapter = UserAdapter()
        viewModel.fetchUsers().observe(viewLifecycleOwner) {
            userAdapter.submitData(lifecycle, it)
        }
        userAdapter.onItemClick = { selected ->
            val toDetailFragment =
                HomeFragmentDirections.actionNavigationHomeToDetailActivity(selected.login)
            findNavController().navigate(toDetailFragment)
        }

        binding.rvGithubUsers.adapter =
            userAdapter.withLoadStateFooter(footer = LoadingStateAdapter {
                userAdapter.retry()
            })
    }
}