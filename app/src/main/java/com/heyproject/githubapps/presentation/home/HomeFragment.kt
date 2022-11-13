package com.heyproject.githubapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.heyproject.githubapps.databinding.FragmentHomeBinding
import com.heyproject.githubapps.presentation.adapter.LoadingStateAdapter
import com.heyproject.githubapps.presentation.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            homeFragment = this@HomeFragment
            rvGithubUsers.adapter = UserAdapter()
            rvGithubUsers.setHasFixedSize(true)
        }

        fetchUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchUsers() {
        userAdapter = UserAdapter()
        viewModel.fetchUsers().observe(viewLifecycleOwner) {
            userAdapter.submitData(lifecycle, it)
        }
        userAdapter.onItemClick = { selected ->
//            val toDetailFragment =
//                HomeFragmentDirections.actionHomeFragmentToStoryDetailFragment(selected)
//            findNavController().navigate(toDetailFragment)
        }

        binding.rvGithubUsers.adapter =
            userAdapter.withLoadStateFooter(footer = LoadingStateAdapter {
                userAdapter.retry()
            })
    }
}