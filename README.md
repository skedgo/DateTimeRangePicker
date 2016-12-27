# DateTimeRangePicker
[![](https://jitpack.io/v/skedgo/DateTimeRangePicker.svg)](https://jitpack.io/#skedgo/DateTimeRangePicker)

A date time range picker for android

![No start date time and no end date time](images/Screenshot_1482250212.png) ![Pick start date](images/Screenshot_1482250219.png)

![Pick start time](images/Screenshot_1482250231.png) ![Have start date time and end date time](images/Screenshot_1482250242.png)

## Usage
Firstly, grab latest release of the library via [JitPack](https://jitpack.io/#skedgo/DateTimeRangePicker). And note that, it utilizes [Joda-Time](https://github.com/dlew/joda-time-android) to process some date time logic under the hood. So you might need to [set up Joda-Time properly](https://github.com/dlew/joda-time-android#usage).

With start and end date times specified:
```kotlin
val intent = DateTimeRangePickerActivity.newIntent(
    context,
    TimeZone.getDefault(),
    DateTime.now().millis,
    DateTime.now().plusDays(2).millis
)
activity.startActivityForResult(intent, RQC_PICK_DATE_TIME_RANGE)
```

Or without start and end date times:
```kotlin
val intent = DateTimeRangePickerActivity.newIntent(
    context,
    TimeZone.getDefault(),
    null, null
)
activity.startActivityForResult(intent, RQC_PICK_DATE_TIME_RANGE)
```

At `onActivityResult()`, `DateTimeRangePickerActivity` will return an `Intent` data having following:  
* `startTimeInMillis` as `Long`
* `endTimeInMillis` as `Long`
* `timeZone` as `String`

## Demo
Run 2 following instrumentation tests on `DateTimeRangePickerActivityTest` to see the 2 usages:
* `withoutStartAndEndDateTimes()`
* `withStartAndEndDateTimes()`
  
