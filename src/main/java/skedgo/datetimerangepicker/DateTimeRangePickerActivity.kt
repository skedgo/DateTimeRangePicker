package skedgo.datetimerangepicker

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.util.Pair
import android.text.format.DateFormat
import com.squareup.timessquare.CalendarPickerView
import org.joda.time.DateTime
import skedgo.anim.AnimatedTransitionActivity
import skedgo.datetimerangepicker.databinding.DateTimeRangePickerBinding
import java.util.*

class DateTimeRangePickerActivity : AnimatedTransitionActivity() {
  companion object {
    fun newIntent(
        context: Context?,
        timeZone: TimeZone?,
        startTimeInMillis: Long?,
        endTimeInMillis: Long?): Intent {
      val intent = Intent(context!!, DateTimeRangePickerActivity::class.java)
      startTimeInMillis?.let { intent.putExtra(DateTimeRangePickerViewModel.KEY_START_TIME_IN_MILLIS, it) }
      endTimeInMillis?.let { intent.putExtra(DateTimeRangePickerViewModel.KEY_END_TIME_IN_MILLIS, it) }
      intent.putExtra(DateTimeRangePickerViewModel.KEY_TIME_ZONE, timeZone!!.id)
      return intent
    }
  }

  private val viewModel: DateTimeRangePickerViewModel by lazy {
    DateTimeRangePickerViewModel(TimeFormatter(applicationContext))
  }
  private val binding: DateTimeRangePickerBinding by lazy {
    DataBindingUtil.setContentView<DateTimeRangePickerBinding>(
        this,
        R.layout.date_time_range_picker
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    setEnteringAnimation(Pair(R.anim.entering_slide_up, R.anim.exiting_fade_out))
    setExitingAnimation(Pair(R.anim.entering_fade_in, R.anim.exiting_slide_down))
    super.onCreate(savedInstanceState)

    viewModel.handleArgs(intent.extras)
    binding.setViewModel(viewModel)

    val toolbar = binding.toolbar
    toolbar.inflateMenu(R.menu.date_time_range_picker)
    toolbar.setNavigationOnClickListener { v -> finish() }
    toolbar.setOnMenuItemClickListener { item ->
      when {
        item.itemId == R.id.dateTimeRangePickerDoneItem -> {
          setResult(Activity.RESULT_OK, viewModel.createResultIntent())
          finish()
        }
      }
      true
    }

    val calendarPickerView = binding.calendarPickerView
    calendarPickerView.init(viewModel.minDate, viewModel.maxDate)
        .inMode(CalendarPickerView.SelectionMode.RANGE)
    viewModel.startDateTime.value?.let { calendarPickerView.selectDate(it.toDate()) }
    viewModel.endDateTime.value?.let { calendarPickerView.selectDate(it.toDate()) }

    calendarPickerView.setOnDateSelectedListener(object : CalendarPickerView.OnDateSelectedListener {
      override fun onDateSelected(date: Date) {
        viewModel.updateSelectedDates(calendarPickerView.selectedDates)
      }

      override fun onDateUnselected(date: Date) {
        viewModel.updateSelectedDates(calendarPickerView.selectedDates)
      }
    })

    binding.pickStartTimeView.setOnClickListener { v ->
      showTimePicker(viewModel.startDateTime.value, viewModel.onStartTimeSelected)
    }
    binding.pickEndTimeView.setOnClickListener { v ->
      showTimePicker(viewModel.endDateTime.value, viewModel.onEndTimeSelected)
    }
  }

  private fun showTimePicker(
      initialTime: DateTime,
      listener: TimePickerDialog.OnTimeSetListener) {
    TimePickerDialog(
        this,
        listener,
        initialTime.hourOfDay,
        initialTime.minuteOfHour,
        DateFormat.is24HourFormat(this)
    ).show()
  }
}
