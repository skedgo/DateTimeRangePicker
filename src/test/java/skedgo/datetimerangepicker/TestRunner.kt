package skedgo.datetimerangepicker

import android.app.Application

import net.danlew.android.joda.JodaTimeAndroid

import org.junit.runners.model.InitializationError
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.manifest.AndroidManifest

/**
 * To workaround https://github.com/robolectric/robolectric/issues/1430.
 */
class TestRunner @Throws(InitializationError::class)
constructor(klass: Class<*>) : RobolectricTestRunner(klass) {
  override fun getAppManifest(config: Config): AndroidManifest {
    val appManifest = super.getAppManifest(config)
    return object : AndroidManifest(
        appManifest.androidManifestFile,
        appManifest.resDirectory,
        appManifest.assetsDirectory
    ) {
      @Throws(Exception::class)
      override fun getRClassName(): String {
        return R::class.java.name
      }

      override fun getApplicationName(): String {
        return TestApp::class.java.name
      }
    }
  }

  internal class TestApp : Application() {
    override fun onCreate() {
      super.onCreate()
      JodaTimeAndroid.init(this)
    }
  }
}