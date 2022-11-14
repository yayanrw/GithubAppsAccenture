package com.heyproject.githubapps.presentation.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.heyproject.githubapps.R
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.databinding.FragmentSearchBinding
import com.heyproject.githubapps.presentation.adapter.FollowAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), MenuProvider, SearchView.OnQueryTextListener {
    private val viewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var followAdapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_search -> {
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchUser(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun searchUser(query: String) {
        followAdapter = FollowAdapter()
        viewModel.searchUser(query).observe(viewLifecycleOwner) { viewResource ->
            when (viewResource) {
                is ViewResource.Loading -> {
                    showLoading(isLoading = true, isEmpty = true)
                }
                is ViewResource.Success -> {
                    followAdapter.submitList(viewResource.data)
                    binding.apply {
                        rvGithubUsers.adapter = followAdapter
                        binding.rvGithubUsers.visibility = View.VISIBLE
                    }

                    if (viewResource.data.isNullOrEmpty()) {
                        showLoading(isLoading = false, isEmpty = true)
                    } else {
                        showLoading(isLoading = false, isEmpty = false)
                    }
                }
                is ViewResource.Error -> {
                    showLoading(isLoading = false, isEmpty = true)
                }
            }
        }

        followAdapter.onItemClick = { selected ->
            val toDetailFragment =
                SearchFragmentDirections.actionNavigationSearchToDetailActivity(selected.login)
            findNavController().navigate(toDetailFragment)
        }
    }

    private fun showLoading(isLoading: Boolean, isEmpty: Boolean) {
        binding.viewTyping.root.visibility = View.GONE
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.viewEmpty.root.visibility = View.GONE
            binding.rvGithubUsers.visibility = View.GONE
        } else {
            if (isEmpty) {
                binding.progressBar.visibility = View.GONE
                binding.viewEmpty.root.visibility = View.VISIBLE
                binding.rvGithubUsers.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.viewEmpty.root.visibility = View.GONE
                binding.rvGithubUsers.visibility = View.VISIBLE
            }
        }
    }

}