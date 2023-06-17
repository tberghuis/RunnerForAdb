package dev.tberghuis.adbrunner.utils

import android.util.Log
import dev.tberghuis.adbrunner.BuildConfig

fun logd(s: String) {
  if (BuildConfig.DEBUG)
    Log.d("xxx", s)
}