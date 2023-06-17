package dev.tberghuis.adbrunner.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import dev.tberghuis.adbrunner.MyApplication
import dev.tberghuis.adbrunner.data.AdbCommand
import dev.tberghuis.adbrunner.runAdb
import dev.tberghuis.adbrunner.utils.logd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val application: Application) : AndroidViewModel(application) {

  private val adbCommandDao = (application as MyApplication).database.adbCommandDao()

  var adbCommandList by mutableStateOf(listOf<AdbCommand>())


  init {
    viewModelScope.launch {
      // doitwrong
      // should probably collectAsStateWithLifecycle
      // in composable
      adbCommandDao.getAll().collect {
        logd("collect $it")
        adbCommandList = it
      }
    }
  }
}