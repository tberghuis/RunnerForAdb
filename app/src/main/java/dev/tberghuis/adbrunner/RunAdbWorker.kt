package dev.tberghuis.adbrunner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dev.tberghuis.adbrunner.utils.logd
import java.io.BufferedReader
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// todo also make a foreground service
// Handler post toast message
class RunAdbWorker(private val context: Context, params: WorkerParameters) :
  CoroutineWorker(context, params) {

  override suspend fun doWork(): Result {
    // doitwrong
    val host = inputData.getString("HOST")
    val command = inputData.getString("ADB_COMMAND")

    logd("dowork start host $host command $command")

    if (host == null || command == null) {
      return Result.failure()
    }

    val adbProcess = runAdbTodoRename(context.applicationContext, host, command)

    // does not work
//    val adbOutput = adbProcess.inputStream.bufferedReader().use(BufferedReader::readText)
//    adbProcess.waitFor()
//    Handler(Looper.getMainLooper()).post {
//      Toast.makeText(
//        context.applicationContext,
//        adbOutput,
//        Toast.LENGTH_LONG
//      ).show()
//    }

    withContext(IO) {
      // it sort of works i guess
      val adbOutput = adbProcess.inputStream.bufferedReader().use(BufferedReader::readText)
      adbProcess.waitFor()
      logd("adbOutput: $adbOutput")
    }
    logd("dowork end")
    return Result.success()
  }
}