package dev.tberghuis.adbrunner.ui

import android.app.Application
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.tberghuis.adbrunner.ui.theme.ADBRunnerTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewScreenPreview() {
  ADBRunnerTheme {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
      NewScreen({}, NewScreenViewModel(Application()))
    }
  }
}