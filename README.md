# Runner for ABD

Save and run ADB commands from your android device

<a href='https://play.google.com/store/apps/details?id=dev.tberghuis.adbrunner'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="200"/></a>

## Usage Instructions

Device that you wish to run ADB commands against should have wifi debugging enabled.

To make your targeted device accept ADB commands you may need to run:

  adb tcpip 5555

You will need to do this using ADB on PC or another app such as LADB.

You can also run ADB command from other apps by sending broadcast with an Intent.

Example code:

```kotlin
val intent = Intent()
intent.action = "dev.tberghuis.adbrunner.RUN_ADB"
intent.putExtra("HOST", "192.168.0.99")
intent.putExtra("ADB_COMMAND", "shell echo hello world")
intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
intent.component =
  ComponentName("dev.tberghuis.adbrunner", "dev.tberghuis.adbrunner.AdbRunnerBroadcastReceiver")
appContext.sendBroadcast(intent)
```

## ADB binary

The `libadb.so` file is copied from the app LADB https://github.com/tytydraco/LADB

## Screenshots

<img alt='screenshot home' src='https://github.com/tberghuis/RunnerForAdb/raw/master/assets/Screenshot_home.png' width="200"/> <img alt='screenshot home' src='https://github.com/tberghuis/RunnerForAdb/raw/master/assets/Screenshot_clear-cache.png' width="200"/> 