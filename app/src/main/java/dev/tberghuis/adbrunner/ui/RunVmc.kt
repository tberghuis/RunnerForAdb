package dev.tberghuis.adbrunner.ui

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dev.tberghuis.adbrunner.runAdbTodoRename
import dev.tberghuis.adbrunner.utils.logd
import java.io.InterruptedIOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

// Vmc = ViewModelComponent allow for reuse
class RunVmc(private val scope: CoroutineScope, val application: Application) {
  val adbCommandOutput = mutableStateOf("")
  val fieldState = AdbCommandFieldState()

  private var adbProcess: Process? = null

  // todo move so can reuse with run screen
  fun runAdbCommand() {
    scope.launch {
      adbCommandOutput.value = ""

      adbProcess = runAdbTodoRename(application, fieldState.host, fieldState.adbCommandString)

      adbProcess!!.inputStream.bufferedReader().lineSequence().asFlow().flowOn(IO).catch { e ->
        logd("caught $e")
        // InterruptedIOException when destroyAdbProcess()
        if (e !is InterruptedIOException) throw e
      }.collect {
        adbCommandOutput.value += "$it\n"
      }
    }
  }

  fun destroyAdbProcess() {
    adbProcess?.destroy()
  }

}