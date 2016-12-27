package skedgo.datetimerangepicker

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.joda.time.DateTime
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DateTimeRangePickerActivityTest {
  @Rule @JvmField val activityTestRule = ActivityTestRule<DateTimeRangePickerActivity>(
      DateTimeRangePickerActivity::class.java,
      true,
      false
  )
  val countDownLatch: CountDownLatch by lazy { CountDownLatch(1) }

  @Before fun setUp() {
    // To terminate the test after pressing back.
    val app = InstrumentationRegistry.getTargetContext().applicationContext as Application
    app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
      override fun onActivityPaused(activity: Activity?) {
      }

      override fun onActivityStarted(activity: Activity?) {
      }

      override fun onActivityDestroyed(activity: Activity?) {
        countDownLatch.countDown()
      }

      override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
      }

      override fun onActivityStopped(activity: Activity?) {
      }

      override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
      }

      override fun onActivityResumed(activity: Activity?) {
      }
    })
  }

  @Test fun withoutStartAndEndDateTimes() {
    activityTestRule.launchActivity(
        DateTimeRangePickerActivity.newIntent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TimeZone.getDefault(),
            null, null
        )
    )
    countDownLatch.await(10, TimeUnit.MINUTES)
  }

  @Test fun withStartAndEndDateTimes() {
    activityTestRule.launchActivity(
        DateTimeRangePickerActivity.newIntent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TimeZone.getDefault(),
            DateTime.now().millis,
            DateTime.now().plusDays(2).millis
        )
    )
    countDownLatch.await(10, TimeUnit.MINUTES)
  }
}
