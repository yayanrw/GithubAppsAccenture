package com.heyproject.githubapps.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.heyproject.githubapps.databinding.FragmentHomeBinding
import com.heyproject.githubapps.presentation.adapter.UserAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

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

        setObserver()
        fetchUsers()
    }

    private fun fetchUsers() {
        TODO("Not yet implemented")
    }

    private fun setObserver() {
        TODO("Not yet implemented")
    }
}