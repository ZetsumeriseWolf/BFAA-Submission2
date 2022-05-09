package com.dicoding.submission2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.Resource
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.data.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class FollowerViewModel: ViewModel() {
    private val followerUser = MutableLiveData<Resource<List<User>>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowerData(username: String): LiveData<Resource<List<User>>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if (response.isSuccessful) followerUser.postValue(Resource.Success(response.body()))
                else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
        return followerUser
    }

    companion object {
        private const val TAG = "FollowerViewModel"
    }

}