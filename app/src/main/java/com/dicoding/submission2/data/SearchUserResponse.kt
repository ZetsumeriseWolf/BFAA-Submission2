package com.dicoding.submission2.data

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
	@field:SerializedName("items")
	val items: List<User>
)
