package com.dicoding.submission2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import android.viewbinding.library.fragment.viewBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.R
import com.dicoding.submission2.Resource
import com.dicoding.submission2.adapter.UserAdapter
import com.dicoding.submission2.data.User
import com.dicoding.submission2.databinding.FragmentFollowingBinding
import com.dicoding.submission2.viewModel.FollowingViewModel
import com.dicoding.submission2.DetailActivity.Companion.EXTRA_DATA

class FollowingFragment : Fragment() {

    private val followingBinding: FragmentFollowingBinding by viewBinding()
    private lateinit var userAdapter: UserAdapter
    private lateinit var followingViewModel: FollowingViewModel
    var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(EXTRA_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)

        userAdapter = UserAdapter()

        followingBinding.apply {
            rvFollowing.layoutManager = LinearLayoutManager(activity)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = userAdapter
        }

        followingViewModel.getFollowingData(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> it.data?.let { dataFollowing -> onSuccessFollowing(dataFollowing) }
                is Resource.Error -> onErrorFollowing(it.message)
            }
        }

        followingViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun onSuccessFollowing(data: List<User>) {
        userAdapter.setAllData(data)
    }

    private fun showLoading(isLoading: Boolean) {
        followingBinding.progressBarFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onErrorFollowing(message: String?) {}
}