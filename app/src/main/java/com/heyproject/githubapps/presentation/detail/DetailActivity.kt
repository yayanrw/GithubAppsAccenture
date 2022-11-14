package com.heyproject.githubapps.presentation.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.heyproject.githubapps.R
import com.heyproject.githubapps.common.ViewResource
import com.heyproject.githubapps.databinding.ActivityDetailBinding
import com.heyproject.githubapps.presentation.adapter.SectionsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailActivityArgs by navArgs()

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar(args.login)
        setViewPager()

        binding.apply {
            viewModel = viewModel
            detailActivity = this@DetailActivity
        }

        setObserver()
        setListener()
    }

    private fun setListener() {
        binding.imgbFavorite.setOnClickListener {
            setFavorite()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            androidx.appcompat.R.id.home -> {
                finish()
                return true
            }
            R.id.action_open_browser -> {
                val openGithub = Intent(Intent.ACTION_VIEW, Uri.parse("$githubUrl${args.login}"))
                startActivity(openGithub)
            }
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
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setActionBar(login: String) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = login
            elevation = 0.0F
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setObserver() {
        viewModel.fetchUserDetail(args.login).observe(this) { userDetailData ->
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

        viewModel.fetchUserDetailFlat(args.login)
        viewModel.userDetail.observe(this) { userDetail ->
            if (userDetail.isFavorite) {
                binding.imgbFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.imgbFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
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

    fun setFavorite() {
        viewModel.userDetail.value?.let { userDetail ->
            viewModel.setFavorite(userDetail, !userDetail.isFavorite)
            viewModel.fetchUserDetailFlat(args.login)
        }
    }

    private fun setViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    companion object {
        private const val githubUrl = "https://github.com/"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers, R.string.following
        )
    }
}