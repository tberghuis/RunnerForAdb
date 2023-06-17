package dev.tberghuis.adbrunner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.adbrunner.utils.logd
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenViewModel(private val application: Application) : AndroidViewModel(application) {
  val adbPath = "${application.applicationInfo.nativeLibraryDir}/libadb.so"

  fun startServer() {
    logd("start-server")
    val command = listOf(adbPath, "start-server")
    runCommand(command)
  }

  fun devices() {
    logd("devices")
    val command = listOf(adbPath, "devices")
    runCommand(command)
  }

  fun connectPoco() {
    logd("connect poco")
    val command = listOf(adbPath, "connect", "192.168.0.183")
    viewModelScope.launch {
      runCommandSuspend(command)
    }
  }

  fun spankbangVlc() {
    // adb -s 192.168.0.183 shell am start -n org.videolan.vlc/.StartActivity -a android.intent.action.VIEW -d \"https://vdownload-43.sb-cd.com/1/3/13909862-720p.mp4?secure=Y4TuLzN9Ot_-GR4ipo4gng,1685552433\&m=43\&d=1\&_tid=13909862\"
    // https://vdownload-44.sb-cd.com/1/3/13997963-720p.mp4?secure=5-M5o1k3loNqF0ZbbL6AZw,1686485221&m=44&d=1&_tid=13997963
    val command = listOf(
      adbPath,
      "-s",
      "192.168.0.183",
      "shell",
      "am",
      "start",
      "-n",
      "org.videolan.vlc/.StartActivity",
      "-a",
      "android.intent.action.VIEW",
      "-d",
      "https://vdownload-44.sb-cd.com/1/3/13997963-720p.mp4?secure=5-M5o1k3loNqF0ZbbL6AZw,1686485221&m=44&d=1&_tid=13997963".replace(
        "&", "\\&"
      )
    )
    viewModelScope.launch {
      runCommandSuspend(command)
    }
  }


  fun inputHello() {
    logd("input hello")
    val command = listOf(adbPath, "-s", "192.168.0.183", "shell", "input", "text", "hello")
    runCommand(command)
  }

  fun apiLevel() {
    logd("adb shell getprop ro.build.version.sdk")
    val command = listOf(adbPath, "-s", "emulator-5554", "shell", "getprop", "ro.build.version.sdk")
    viewModelScope.launch {
      runCommandSuspend(command)
    }
  }


  fun multipleCommands() {
    val command1 = listOf(adbPath, "connect", "192.168.0.183")
    val command2 = listOf(adbPath, "devices")
    val command3 = listOf(adbPath, "-s", "192.168.0.183", "shell", "input", "text", "hello")

    viewModelScope.launch {
      runCommandSuspend(command1)
      logd("after command1")
      runCommandSuspend(command2)
      logd("after command2")
      runCommandSuspend(command3)
      logd("after command3")
    }
  }


  private fun runCommand(command: List<String>) {
    val context = application
    val processBuilder = ProcessBuilder(command).directory(context.filesDir).apply {
      redirectErrorStream(true)
      environment().apply {
        put("HOME", context.filesDir.path)
        put("TMPDIR", context.cacheDir.path)
      }
    }

    viewModelScope.launch(IO) {
      try {
        val process: Process = processBuilder.start()
        logd("1")
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val line = reader.readText();
        println(line)
        val exitVal = process.waitFor()
        if (exitVal == 0) {
          println("Success! $command")
          //exitProcess(0)
        } else {
          //else...
          println("error $exitVal $command")
        }
      } catch (e: IOException) {
        e.printStackTrace()
      } catch (e: InterruptedException) {
        e.printStackTrace()
      }
    }
  }

  // todo return result
  private suspend fun runCommandSuspend(command: List<String>): String {
    val context = application
    val processBuilder = ProcessBuilder(command).directory(context.filesDir).apply {
      redirectErrorStream(true)
      environment().apply {
        put("HOME", context.filesDir.path)
        put("TMPDIR", context.cacheDir.path)
      }
    }

    val process: Process = withContext(IO) {
      processBuilder.start()
    }
    val reader = BufferedReader(InputStreamReader(process.inputStream))
    val line = reader.readText();
    println(line)
    val exitVal = withContext(IO) {
      process.waitFor()
    }
    if (exitVal == 0) {
      println("Success! $command")
      //exitProcess(0)
      return "Success! $command"
    } else {
      //else...
      println("error $exitVal $command")
      return "error $exitVal $command"
    }
  }
}