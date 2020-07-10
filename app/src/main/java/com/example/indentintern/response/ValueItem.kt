package com.example.indentintern.response

import com.google.gson.annotations.SerializedName

data class ValueItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("categories")
	val categories: List<String>? = null,

	@field:SerializedName("joke")
	val joke: String? = null
)