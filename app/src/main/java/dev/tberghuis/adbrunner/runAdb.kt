package dev.tberghuis.adbrunner

import android.content.Context

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

