package com.heyproject.githubapps.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.heyproject.githubapps.R
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.databinding.FragmentFollowersBinding
import com.heyproject.githubapps.presentation.adapter.FollowAdapter

class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private lateinit var followAdapter: FollowAdapter
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
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

        fetchFollowers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchFollowers() {
        followAdapter = FollowAdapter()
        viewModel.login.observe(viewLifecycleOwner) { login ->
            viewModel.fetchFollowers(login).observe(viewLifecycleOwner) { viewResource ->
                when (viewResource) {
                    is ViewResource.Loading -> {
                        setLoading(true)
                    }
                    is ViewResource.Success -> {
                        setLoading(false)
                        followAdapter.submitList(viewResource.data)
                        binding.rvGithubUsers.adapter = followAdapter

                        viewResource.data?.isEmpty()?.let { setVisibility(it) }
                    }
                    is ViewResource.Error -> {
                        setLoading(false)
                        binding.apply {
                            progressBar.visibility = View.GONE
                            rvGithubUsers.visibility = View.GONE
                            noData.text = getString(R.string.oops_something_went_wrong)
                            noData.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setVisibility(isEmpty: Boolean) {
        if (isEmpty) {
            binding.apply {
                progressBar.visibility = View.GONE
                rvGithubUsers.visibility = View.GONE
                noData.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                rvGithubUsers.visibility = View.VISIBLE
                noData.visibility = View.GONE
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                progressBar.visibility = View.VISIBLE
                rvGithubUsers.visibility = View.GONE
            }
        } else {
            binding.apply {
                progressBar.visibility = View.GONE
                rvGithubUsers.visibility = View.VISIBLE
            }
        }
    }
}