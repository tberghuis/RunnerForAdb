package dev.tberghuis.adbrunner.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.tberghuis.adbrunner.MyApplication
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class NewScreenViewModel(application: Application) : AndroidViewModel(application) {
  private val adbCommandDao = (application as MyApplication).database.adbCommandDao()
  val runVmc = RunVmc(viewModelScope, application)
  fun insertNewAdbCommand() {
    viewModelScope.launch(IO) {
      adbCommandDao.insertAll(runVmc.fieldState.toAdbCommand())
    }
  }
}
