package com.heyproject.githubapps.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.databinding.FragmentFollowingsBinding
import com.heyproject.githubapps.presentation.adapter.FollowAdapter

class FollowingsFragment : Fragment() {
    private var _binding: FragmentFollowingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var followAdapter: FollowAdapter
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            rvGithubUsers.apply {
                adapter = FollowAdapter()
                setHasFixedSize(true)
            }
        }

        fetchFollowings()
    }

    private fun fetchFollowings() {
        followAdapter = FollowAdapter()
        viewModel.login.observe(viewLifecycleOwner) { login ->
            viewModel.getFollowings(login).observe(viewLifecycleOwner) { viewResource ->
                when (viewResource) {
                    is ViewResource.Loading -> {
                        setLoading(true)
                    }
                    is ViewResource.Success -> {
                        setLoading(false)
                        followAdapter.submitList(viewResource.data)
                        binding.rvGithubUsers.adapter = followAdapter
                    }
                    is ViewResource.Error -> {
                        setLoading(false)
                    }
                }
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvGithubUsers.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvGithubUsers.visibility = View.VISIBLE
        }
    }
}