package com.dicoding.submission2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.submission2.Resource
import com.dicoding.submission2.api.ApiConfig
import com.dicoding.submission2.data.SearchUserResponse
import com.dicoding.submission2.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val userData = MutableLiveData<Resource<List<User>>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getUserData(q: String): LiveData<Resource<List<User>>> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUsers(q)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) userData.postValue(Resource.Success(response.body()?.items))
                else Log.e(TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
        return userData
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

}