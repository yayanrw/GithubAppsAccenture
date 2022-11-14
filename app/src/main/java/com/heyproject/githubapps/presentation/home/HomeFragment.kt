package com.heyproject.githubapps.presentation.home

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.heyproject.githubapps.R
import com.heyproject.githubapps.databinding.FragmentHomeBinding
import com.heyproject.githubapps.presentation.adapter.LoadingStateAdapter
import com.heyproject.githubapps.presentation.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuProvider {
    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.option_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.setting_action -> {
                findNavController().navigate(R.id.action_navigation_home_to_detailActivity)
                true
            }
            else -> {
                true
            }
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