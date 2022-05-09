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

class DetailViewModel: ViewModel() {
   private val userDetail = MutableLiveData<Resource<User>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(username: String?): LiveData<Resource<User>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username!!)
        client.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                _isLoading.value = false
                if (response.isSuccessful) userDetail.postValue(Resource.Success(response.body()))
                else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
        return userDetail
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}