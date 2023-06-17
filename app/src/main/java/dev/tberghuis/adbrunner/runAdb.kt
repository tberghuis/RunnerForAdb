package dev.tberghuis.adbrunner

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

// todo return result
suspend fun runAdb(appContext: Context, host: String, command: String) {
  val adbPath = "${appContext.applicationInfo.nativeLibraryDir}/libadb.so"
  val commandList = listOf(
    "sh",
    "-c",
    "$adbPath connect $host; $adbPath -s $host $command",
  )

  val processBuilder = ProcessBuilder(commandList).directory(appContext.filesDir).apply {
    redirectErrorStream(true)
    environment().apply {
      put("HOME", appContext.filesDir.path)
      put("TMPDIR", appContext.cacheDir.path)
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
    println("Success! $commandList")
  } else {
    println("error $exitVal $commandList")
  }
}

// doitwrong
// if i need result emit custom type,[line, end success, end fail]
fun runAdbTodoRename(appContext: Context, host: String, command: String): Process {
  val adbPath = "${appContext.applicationInfo.nativeLibraryDir}/libadb.so"
  val commandList = listOf(
    "sh",
    "-c",
    "$adbPath connect $host; $adbPath -s $host $command",
  )

  val processBuilder = ProcessBuilder(commandList).directory(appContext.filesDir).apply {
    redirectErrorStream(true)
    environment().apply {
      put("HOME", appContext.filesDir.path)
      put("TMPDIR", appContext.cacheDir.path)
    }
  }

  return processBuilder.start()
}

