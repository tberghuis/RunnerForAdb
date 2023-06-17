package dev.tberghuis.adbrunner.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.tberghuis.adbrunner.utils.logd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  navigateNewScreen: () -> Unit,
  navigateRunScreen: (Int) -> Unit,
  vm: HomeViewModel = viewModel()
) {

  Scaffold(
    topBar = { TopAppBar(title = { Text("Runner for ADB") }) },
    floatingActionButton = {
      FloatingActionButton(
        onClick = navigateNewScreen,
      ) {
        Icon(
          Icons.Rounded.Add,
          contentDescription = "add ADB command"
        )
      }
    }


  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .padding(paddingValues)
        .fillMaxSize(),
      contentPadding = PaddingValues(10.dp),
    ) {

      items(vm.adbCommandList) { adbCommand ->
        Card(modifier = Modifier
          .padding(bottom = 10.dp)
          .clickable {
            navigateRunScreen(adbCommand.id)
          }
        ) {
          Text(
            adbCommand.title,
            modifier = Modifier
              .padding(10.dp)
              .fillMaxWidth(),
          )
        }
      }
    }
  }
}