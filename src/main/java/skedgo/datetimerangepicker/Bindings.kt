package skedgo.datetimerangepicker

import android.databinding.BindingAdapter
import android.support.v7.widget.Toolbar
import android.view.View

object Bindings {
  @BindingAdapter("isDone") @JvmStatic
  fun setIsDone(v: Toolbar, isDone: Boolean) {
    val item = v.menu.findItem(R.id.dateTimeRangePickerDoneItem)
    item!!.isEnabled = isDone
  }

  @BindingAdapter("isVisible") @JvmStatic
  fun setIsVisible(v: View, isVisible: Boolean) {
    v.visibility = if (isVisible) View.VISIBLE else View.GONE
  }
}
