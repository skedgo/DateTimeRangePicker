package skedgo.datetimerangepicker

import android.app.TimePickerDialog
import android.content.Intent
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Bundle
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import rx.Observable
import rx.subjects.BehaviorSubject
import java.util.*

class DateTimeRangePickerViewModel(private val timeFormatter: TimeFormatter) {
  val startDateText = ObservableField<String>()
  val startTimeText = ObservableField<String>()
  val endDateText = ObservableField<String>()
  val endTimeText = ObservableField<String>()
  val hasStartDate = ObservableBoolean()
  val hasEndDate = ObservableBoolean()
  val isCompletable = ObservableBoolean()
  val onStartTimeSelected: TimePickerDialog.OnTimeSetListener
  val onEndTimeSelected: TimePickerDialog.OnTimeSetListener
  val dateFormatter: DateTimeFormatter = DateTimeFormat.mediumDate()

  internal var timeZone: TimeZone? = TimeZone.getDefault()
  internal val startDateTime: BehaviorSubject<DateTime> = BehaviorSubject.create()
  internal val endDateTime: BehaviorSubject<DateTime> = BehaviorSubject.create()
  internal val minDate: Date by lazy { DateTime.now(DateTimeZone.forTimeZone(timeZone)).toDate() }
  internal val maxDate: Date by lazy {
    DateTime.now(DateTimeZone.forTimeZone(timeZone))
        .plusYears(1)
        .toDate()
  }

  init {
    onStartTimeSelected = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
      startDateTime.value!!.let {
        val newValue = it.withHourOfDay(hourOfDay).withMinuteOfHour(minute)
        startDateTime.onNext(newValue)
      }
    }
    onEndTimeSelected = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
      endDateTime.value!!.let {
        val newValue = it.withHourOfDay(hourOfDay).withMinuteOfHour(minute)
        endDateTime.onNext(newValue)
      }
    }
    startDateTime.subscribe({
      onDateTimeEmitted(it, startDateText, startTimeText, hasStartDate)
    })
    endDateTime.subscribe({
      onDateTimeEmitted(it, endDateText, endTimeText, hasEndDate)
    })
    Observable.combineLatest(startDateTime, endDateTime, {
      x, y ->
      x != null && y != null
    }).subscribe({ isCompletable.set(it) })
  }

  fun onDateTimeEmitted(
      dateTime: DateTime?,
      dateText: ObservableField<String>,
      timeText: ObservableField<String>,
      visibility: ObservableBoolean) {
    if (dateTime != null) {
      visibility.set(true)
      dateText.set(dateFormatter.print(dateTime))
      timeText.set(timeFormatter.printTime(dateTime))
    } else {
      visibility.set(false)
      dateText.set(null)
      timeText.set(null)
    }
  }

  fun createResultIntent(): Intent {
    return Intent()
        .putExtra(KEY_START_TIME_IN_MILLIS, startDateTime.value!!.millis)
        .putExtra(KEY_END_TIME_IN_MILLIS, endDateTime.value!!.millis)
        .putExtra(KEY_TIME_ZONE, timeZone)
  }

  fun updateSelectedDates(selectedDates: List<Date>) {
    if (selectedDates.isEmpty()) {
      startDateTime.onNext(null)
      endDateTime.onNext(null)
      return
    }
    startDateTime.onNext(DateTime(selectedDates.first().time))
    when {
      selectedDates.size == 1 -> endDateTime.onNext(null)
      else -> endDateTime.onNext(DateTime(selectedDates.last().time))
    }
  }

  fun handleArgs(arguments: Bundle) {
    val timeZoneId = arguments.getString(KEY_TIME_ZONE)
    timeZone = TimeZone.getTimeZone(timeZoneId)

    if (arguments.containsKey(KEY_START_TIME_IN_MILLIS)) {
      val startInMillis = arguments.getLong(KEY_START_TIME_IN_MILLIS)
      startDateTime.onNext(DateTime(startInMillis, DateTimeZone.forTimeZone(timeZone)))
    }
    if (arguments.containsKey(KEY_END_TIME_IN_MILLIS)) {
      val endInMillis = arguments.getLong(KEY_END_TIME_IN_MILLIS)
      endDateTime.onNext(DateTime(endInMillis, DateTimeZone.forTimeZone(timeZone)))
    }
  }

  companion object {
    val KEY_START_TIME_IN_MILLIS = "startTimeInMillis"
    val KEY_END_TIME_IN_MILLIS = "endTimeInMillis"
    val KEY_TIME_ZONE = "timeZone"
  }
}
