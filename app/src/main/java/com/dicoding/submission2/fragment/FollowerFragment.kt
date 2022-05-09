package com.dicoding.submission2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.DetailActivity.Companion.EXTRA_DATA
import com.dicoding.submission2.R
import com.dicoding.submission2.Resource
import com.dicoding.submission2.adapter.UserAdapter
import com.dicoding.submission2.data.User
import com.dicoding.submission2.databinding.FragmentFollowerBinding
import com.dicoding.submission2.viewModel.FollowerViewModel

class FollowerFragment : Fragment() {

    private val followerBinding: FragmentFollowerBinding by viewBinding()
    private lateinit var userAdapter: UserAdapter
    private lateinit var followerViewModel: FollowerViewModel
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
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followerViewModel = ViewModelProvider(this).get(
            FollowerViewModel::class.java)

        userAdapter = UserAdapter()

        followerBinding.apply {
            rvFollower.layoutManager = LinearLayoutManager(activity)
            rvFollower.setHasFixedSize(true)
            rvFollower.adapter = userAdapter
        }

        followerViewModel.getFollowerData(username.toString()).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> it.data?.let { dataFollower -> onSuccessFollower(dataFollower) }
                is Resource.Error -> onErrorFollower(it.message)
            }
        }

        followerViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun onSuccessFollower(data: List<User>) {
        userAdapter.setAllData(data)
    }

    private fun showLoading(isLoading: Boolean) {
        followerBinding.progressBarFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onErrorFollower(message: String?) {}
}