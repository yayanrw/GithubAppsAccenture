package com.heyproject.githubapps.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.heyproject.githubapps.R
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(), MenuProvider {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            detailFragment = this@DetailFragment
        }

        setObserver()
    }

    private fun setObserver() {
        viewModel.getUserDetail(args.login).observe(viewLifecycleOwner) { userDetailData ->
            when (userDetailData) {
                is ViewResource.Loading -> {
                    showLoading(true)
                }
                is ViewResource.Success -> {
                    showLoading(false)
                    binding.apply {
                        userDetail = userDetailData.data
                        tvCountPublicRepos.text = userDetailData.data?.publicRepos.toString()
                        tvCountFollowers.text = userDetailData.data?.followers.toString()
                        tvCountFollowing.text = userDetailData.data?.following.toString()
                    }
                }
                is ViewResource.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND

                    val text = """${binding.tvName.text}
@${args.login}

Work at ${binding.tvCompany.text}
Blog Site: ${binding.tvBlog.text}
Public Repos: ${binding.tvCountPublicRepos.text}
Followers: ${binding.tvCountFollowers.text}
Following: ${binding.tvCountFollowing.text}
"""

                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            else -> {
                true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.lnrContent.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.lnrContent.visibility = View.VISIBLE
        }
    }

    fun goToGithub() {
        val openGithub = Intent(Intent.ACTION_VIEW, Uri.parse("$githubUrl${args.login}"))
        startActivity(openGithub)
    }

    companion object {
        private const val githubUrl = "https://github.com/"
    }
}