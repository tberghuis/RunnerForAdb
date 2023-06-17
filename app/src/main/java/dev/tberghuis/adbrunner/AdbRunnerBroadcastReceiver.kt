package dev.tberghuis.adbrunner

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import dev.tberghuis.adbrunner.utils.logd

class AdbRunnerBroadcastReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context?, intent: Intent?) {
    logd("AdbRunnerBroadcastReceiver onReceive intent $intent")

    if (context == null || intent == null) {
      return
    }
    // start work
    val host = intent.getStringExtra("HOST")
    val command = intent.getStringExtra("ADB_COMMAND")

    logd("AdbRunnerBroadcastReceiver host $host command $command")

    val data = Data.Builder()
      .putString("HOST", host)
      .putString("ADB_COMMAND", command)
      .build()

    // todo, alternative would be to run in a foreground service
    // then display a toast with ADB output
    val worker = OneTimeWorkRequestBuilder<RunAdbWorker>()
      .setInputData(data)
      .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
      .build()

    WorkManager.getInstance(context).enqueue(worker)
  }
}