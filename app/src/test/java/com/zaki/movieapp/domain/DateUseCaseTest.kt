package com.zaki.movieapp.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateUseCaseTest {

    private val dateUseCase = DateUseCase()

    @Test
    fun testConvertDate() {
        val currentDate = "2022-09-27"
        val formattedDate = dateUseCase.convertDate(currentDate)

        assertThat(formattedDate).isEqualTo("27 September 2022")
    }
}