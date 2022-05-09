package com.dicoding.submission2.api

import com.dicoding.submission2.*
import com.dicoding.submission2.data.SearchUserResponse
import com.dicoding.submission2.data.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Headers("Authorization: token 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")

    @GET("search/users")
    fun getListUsers(@Query("q") q: String): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<User>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<User>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<User>>
}