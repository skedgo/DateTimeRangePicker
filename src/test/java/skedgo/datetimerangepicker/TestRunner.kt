package skedgo.datetimerangepicker

import android.app.Application

import net.danlew.android.joda.JodaTimeAndroid

import org.junit.runners.model.InitializationError
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.manifest.AndroidManifest
import org.robolectric.res.FileFsFile

/**
 * To workaround https://github.com/robolectric/robolectric/issues/1430.
 */
class TestRunner @Throws(InitializationError::class)
constructor(klass: Class<*>) : RobolectricTestRunner(klass) {
  override fun getAppManifest(config: Config): AndroidManifest {
    val appManifest = super.getAppManifest(config)

    // After upgrading to gradle plugin v2.2.0, the file path of
    // the AM file no longer contains `full` but `aapt`.
    // As a result, the RobolectricTestRunner complained
    // that it didn't find the AM file, resulting in a bunch of failures.
    // So this patch was born to fix that issue. Note that it might be
    // incompatible w/ future releases of Robolectric.
    val newAndroidManifestFilePath = appManifest.androidManifestFile.path.replace("full", "aapt")

    return object : AndroidManifest(
        FileFsFile.from(newAndroidManifestFilePath),
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