package com.zaki.movieapp.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateUtilTest {

  @Test fun testConvertDate() {
    val currentDate = "2022-09-27"
    val formattedDate = DateUtil.convertDate(currentDate)

    assertThat(formattedDate).isEqualTo("27 September 2022")
  }

  @Test fun testErrorDate() {
    val currentDate = ""
    val formattedDate = DateUtil.convertDate(currentDate)

    assertThat(formattedDate).isEqualTo("")
  }
}