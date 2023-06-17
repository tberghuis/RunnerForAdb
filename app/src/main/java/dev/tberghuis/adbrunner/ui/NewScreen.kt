package dev.tberghuis.adbrunner.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewScreen(
  popHome: () -> Unit, vm: NewScreenViewModel = viewModel()
) {

  Scaffold(
    topBar = { TopAppBar(title = { Text("New ADB Command") }) },
  ) { padding ->
    AdbCommandDetails(
      padding,
      vm.runVmc
    ) {
      Button(
        modifier = Modifier.padding(end=10.dp),
        onClick = {
        vm.insertNewAdbCommand()
        popHome()
      }) {
        Text("ADD")
      }
      Button(onClick = {
        popHome()
      }) {
        Text("CANCEL")
      }
    }
  }
}

