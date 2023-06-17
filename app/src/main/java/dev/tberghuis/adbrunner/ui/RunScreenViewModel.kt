package dev.tberghuis.adbrunner.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.adbrunner.MyApplication
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class RunScreenViewModel(
  application: Application,
  savedStateHandle: SavedStateHandle,
) : AndroidViewModel(application) {

  private val adbCommandDao = (application as MyApplication).database.adbCommandDao()
  private val id: Int = checkNotNull(savedStateHandle["id"])
  val runVmc = RunVmc(viewModelScope, application)
  var initialised by mutableStateOf(false)

  init {
    viewModelScope.launch(Main) {
      val adbCommand = adbCommandDao.loadAdbCommandById(id)
      runVmc.fieldState.loadFromAdbCommand(adbCommand)
      initialised = true
    }
  }

  fun saveAdbCommand() {
    val adbCommand = runVmc.fieldState.toAdbCommand().copy(id = id)
    viewModelScope.launch(IO) {
      adbCommandDao.update(adbCommand)
    }
  }

  fun deleteAdbCommand() {
    viewModelScope.launch(IO) {
      adbCommandDao.deleteById(id)
    }
  }
}