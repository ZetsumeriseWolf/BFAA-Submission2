package com.dicoding.submission2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.Resource
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    private val followingUser = MutableLiveData<Resource<List<User>>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowingData(username: String): LiveData<Resource<List<User>>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if (response.isSuccessful) followingUser.postValue(Resource.Success(response.body()))
                else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
        return followingUser
    }

    companion object {
        private const val TAG = "FollowingViewModel"
    }
}