package dev.tberghuis.adbrunner.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.tberghuis.adbrunner.data.AdbCommand

class AdbCommandFieldState {
  var title by mutableStateOf("")
  var host by mutableStateOf("")
  var adbCommandString by mutableStateOf("")

  fun toAdbCommand(): AdbCommand {
    return AdbCommand(
      title = title,
      host = host,
      adbCommandString = adbCommandString,
    )
  }

  fun loadFromAdbCommand(adbCommand: AdbCommand) {
    title = adbCommand.title
    host = adbCommand.host
    adbCommandString = adbCommand.adbCommandString
  }

}