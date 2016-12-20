package skedgo.datetimerangepicker

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid

class TestApp : Application() {
  override fun onCreate() {
    super.onCreate()
    JodaTimeAndroid.init(this)
  }
}
