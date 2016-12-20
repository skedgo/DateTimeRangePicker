package skedgo.datetimerangepicker

import android.os.Bundle
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.annotation.Config
import java.util.*

@RunWith(TestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class DateTimeRangePickerViewModelTest {
  val timeFormatter: TimeFormatter by lazy { mock(TimeFormatter::class.java) }
  val viewModel: DateTimeRangePickerViewModel by lazy {
    DateTimeRangePickerViewModel(timeFormatter)
  }

  @Test fun shouldExtractTimeZoneFromArgs() {
    val args = Bundle()
    args.putString("timeZone", "CET")
    viewModel.handleArgs(args)
    assertThat(viewModel.timeZone).isEqualTo(TimeZone.getTimeZone("CET"))
  }
}
