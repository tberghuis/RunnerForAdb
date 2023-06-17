package dev.tberghuis.adbrunner

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dev.tberghuis.adbrunner.utils.logd
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// todo delete this

class AdbRunnerService : Service() {
  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//    return super.onStartCommand(intent, flags, startId)

    logd("onStartCommand")

    if (intent == null) {
      // stop self here???
      return START_NOT_STICKY
    }

    val action = intent.action
    val host = intent.getStringExtra("HOST")
    val command = intent.getStringExtra("ADB_COMMAND")

    logd("action: $action host: $host command: $command")

    // doitwrong
    GlobalScope.launch(IO) {
//      runAdb()
    }

    return START_NOT_STICKY
  }
}