package com.zaki.movieapp.helper

import com.google.gson.annotations.SerializedName

data class ResponseError(
  @SerializedName("status_message") val statusMessage: String? = null,
  @SerializedName("status_code") val statusCode: Int? = null
)
