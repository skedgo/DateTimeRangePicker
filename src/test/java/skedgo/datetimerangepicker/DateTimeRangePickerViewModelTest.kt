package skedgo.datetimerangepicker

import android.os.Bundle
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*

@RunWith(TestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class DateTimeRangePickerViewModelTest {
  val timeFormatter: TimeFormatter = mock()
  val viewModel: DateTimeRangePickerViewModel by lazy {
    DateTimeRangePickerViewModel(timeFormatter)
  }

  @Test fun shouldExtractTimeZoneFromArgs() {
    val args = Bundle()
    args.putString("timeZone", "CET")
    viewModel.handleArgs(args)
    assertThat(viewModel.timeZone).isEqualTo(TimeZone.getTimeZone("CET"))
  }

  @Test fun shouldExtractStartAndEndDateTimesFromArgs() {
    whenever(timeFormatter.printTime(any())).thenReturn("")

    val startDateTime = DateTime(DateTimeZone.forID("CET"))
    val endDateTime = startDateTime.plusDays(2)

    val args = Bundle()
    args.putString("timeZone", "CET")
    args.putLong("startTimeInMillis", startDateTime.millis)
    args.putLong("endTimeInMillis", endDateTime.millis)

    viewModel.handleArgs(args)

    assertThat(viewModel.startDateTime.value!!).isEqualTo(startDateTime)
    assertThat(viewModel.endDateTime.value!!).isEqualTo(endDateTime)
  }
}
