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

  @Test fun shouldPutTimeZoneIdToResultIntent() {
    whenever(timeFormatter.printTime(any())).thenReturn("")

    val startDateTime = DateTime.now()
    val endDateTime = startDateTime.plusDays(1)

    viewModel.timeZone = TimeZone.getTimeZone("CET")
    viewModel.startDateTime.onNext(startDateTime)
    viewModel.endDateTime.onNext(endDateTime)

    val resultIntent = viewModel.createResultIntent()
    assertThat(resultIntent.getStringExtra("timeZone")).isEqualTo("CET")
  }

  @Test fun shouldPickOneDateForBothStartAndEndDateTimes() {
    viewModel.timeZone = TimeZone.getTimeZone("CET")
    val selectedDateTime = DateTime.now(DateTimeZone.forID("CET"));

    viewModel.updateSelectedDates(listOf(selectedDateTime.toDate()))
    assertThat(viewModel.startDateTime.value).isEqualTo(selectedDateTime)
    assertThat(viewModel.endDateTime.value).isEqualTo(selectedDateTime)
  }

  @Test fun shouldKeepTimesAfterPickingSameDateForBothStartAndEnd() {
    viewModel.timeZone = TimeZone.getTimeZone("CET")

    val startDateTime = DateTime(DateTimeZone.forID("CET"))
        .withYear(2014).withMonthOfYear(12).withDayOfMonth(22)
        .withHourOfDay(10).withMinuteOfHour(30).withSecondOfMinute(20)
    viewModel.startDateTime.onNext(startDateTime)

    val endDateTime = DateTime(DateTimeZone.forID("CET"))
        .withYear(2014).withMonthOfYear(12).withDayOfMonth(23)
        .withHourOfDay(12).withMinuteOfHour(30).withSecondOfMinute(30)
    viewModel.endDateTime.onNext(endDateTime)

    viewModel.updateSelectedDates(listOf(
        DateTime(DateTimeZone.forID("CET"))
            .withYear(2014).withMonthOfYear(12).withDayOfMonth(25)
            .withHourOfDay(4).withMinuteOfHour(0).withSecondOfMinute(10)
            .toDate()
    ))
    assertThat(viewModel.startDateTime.value.toLocalTime())
        .isEqualTo(startDateTime.toLocalTime())
    assertThat(viewModel.endDateTime.value.toLocalTime())
        .isEqualTo(endDateTime.toLocalTime())
  }

  @Test fun shouldKeepTimesAfterPickingDifferentDatesForBothStartAndEnd() {
    viewModel.timeZone = TimeZone.getTimeZone("CET")

    val startDateTime = DateTime(DateTimeZone.forID("CET"))
        .withYear(2014).withMonthOfYear(12).withDayOfMonth(22)
        .withHourOfDay(10).withMinuteOfHour(30).withSecondOfMinute(20)
    viewModel.startDateTime.onNext(startDateTime)

    val endDateTime = DateTime(DateTimeZone.forID("CET"))
        .withYear(2014).withMonthOfYear(12).withDayOfMonth(23)
        .withHourOfDay(12).withMinuteOfHour(30).withSecondOfMinute(30)
    viewModel.endDateTime.onNext(endDateTime)

    viewModel.updateSelectedDates(listOf(
        DateTime(DateTimeZone.forID("CET"))
            .withYear(2014).withMonthOfYear(12).withDayOfMonth(25)
            .withHourOfDay(4).withMinuteOfHour(0).withSecondOfMinute(10)
            .toDate(),
        DateTime(DateTimeZone.forID("CET"))
            .withYear(2014).withMonthOfYear(12).withDayOfMonth(26)
            .withHourOfDay(5).withMinuteOfHour(30).withSecondOfMinute(40)
            .toDate()
    ))
    assertThat(viewModel.startDateTime.value.toLocalTime())
        .isEqualTo(startDateTime.toLocalTime())
    assertThat(viewModel.endDateTime.value.toLocalTime())
        .isEqualTo(endDateTime.toLocalTime())
  }
}
