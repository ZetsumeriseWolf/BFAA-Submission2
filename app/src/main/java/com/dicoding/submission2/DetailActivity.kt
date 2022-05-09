package com.dicoding.submission2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.submission2.adapter.SectionsPagerAdapter
import com.dicoding.submission2.databinding.ActivityDetailBinding
import com.dicoding.submission2.viewModel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

import com.dicoding.submission2.data.User
import com.google.android.material.tabs.TabLayout

class DetailActivity : AppCompatActivity() {

    private lateinit var detailbinding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailbinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailbinding.root)

        val username = intent.getStringExtra(EXTRA_DATA)

        supportActionBar?.title = "Detail User"

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        detailViewModel.getUserDetail(username).observe(this) {
            when (it) {
                is Resource.Success -> onSuccessDetailActivity(it.data)
                is Resource.Error -> onErrorDetailActivity(it.message)
            }
        }


        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val bundle = Bundle()
        bundle.putString(EXTRA_DATA, username)
        val mFragmentManager = supportFragmentManager
        val sectionsPagerAdapter = SectionsPagerAdapter(this, mFragmentManager, bundle)
        detailbinding.viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = detailbinding.tabs
        TabLayoutMediator(tabs, detailbinding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun onSuccessDetailActivity(data: User?) {
        detailbinding.apply {
            Glide.with(this@DetailActivity)
                .load(data?.avatarUrl)
                .circleCrop()
                .into(civUserDetail)
            usernameDetail.text = data?.username
            nameDetail.text = data?.name
            companyDetail.text = data?.company
            locationDetail.text = data?.location
            followerDetail.text = data?.followers?.toString()
            followingDetail.text = data?.following?.toString()
            repositoryDetail.text = data?.publicRepos?.toString()
        }
    }

    private fun onErrorDetailActivity(message: String?) {}

    private fun showLoading(isLoading: Boolean) {
        detailbinding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DATA = "EXTRA_DATA"

        @StringRes
        val TAB_TITLES = intArrayOf(
            R.string.tab_follower,
            R.string.tab_following
        )
    }
}