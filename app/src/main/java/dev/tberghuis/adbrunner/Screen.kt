package dev.tberghuis.adbrunner

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Screen(
  vm: ScreenViewModel = viewModel()
) {
  Column {

    Button(onClick = {
      vm.startServer()
    }) {
      Text("start server")
    }

    Button(onClick = {
      vm.devices()
    }) {
      Text("adb devices")
    }



    Button(onClick = {
      vm.inputHello()
    }) {
      Text("input hello")
    }

    Button(onClick = {
      vm.apiLevel()
    }) {
      Text("api level")
    }

    Button(onClick = {
      vm.multipleCommands()
    }) {
      Text("multiple commands")
    }

    Button(onClick = {
      vm.connectPoco()
    }) {
      Text("connect poco")
    }

    Button(onClick = {
      vm.spankbangVlc()
    }) {
      Text("spankbangVlc")
    }

  }
}