package com.zaki.movieapp.domain

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateUseCase @Inject constructor() {

    fun convertDate(currentDate: String): String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(currentDate)
            val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            dateFormatter.format(sdf!!)
        } catch (e: Exception) {
            ""
        }
    }
}