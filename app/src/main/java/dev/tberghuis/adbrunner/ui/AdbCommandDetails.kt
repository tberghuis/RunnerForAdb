package dev.tberghuis.adbrunner.ui

import android.view.KeyEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun AdbCommandDetails(
  padding: PaddingValues,
  runVmc: RunVmc,
  formActions: @Composable () -> Unit
) {

//  val columnWidth = Modifier.then(
//    if (LocalConfiguration.current.screenWidthDp > 500) Modifier.width(450.dp)
//    else Modifier.fillMaxWidth()
//  )


  val focusManager = LocalFocusManager.current

  val fieldModifier = Modifier
    .fillMaxWidth()
    .onPreviewKeyEvent {
      if (it.nativeKeyEvent.keyCode != KeyEvent.KEYCODE_ENTER) {
        return@onPreviewKeyEvent false
      }
      if (it.type == KeyEventType.KeyUp) {
        focusManager.moveFocus(
          focusDirection = FocusDirection.Next,
        )
      }
      true
    }


  Column(
    modifier = Modifier
      .padding(padding)
      .padding(10.dp)
      .verticalScroll(rememberScrollState())
      .fillMaxWidth()
  ) {
    OutlinedTextField(
      modifier = fieldModifier,
      value = runVmc.fieldState.title,
      onValueChange = {
        runVmc.fieldState.title = it
      },
      label = { Text("Title") },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
      singleLine = true
    )
    OutlinedTextField(
      modifier = fieldModifier,
      value = runVmc.fieldState.host,
      onValueChange = {
        runVmc.fieldState.host = it
      },
      label = { Text("Host") },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
      singleLine = true
    )
    OutlinedTextField(
      modifier = fieldModifier,
      value = runVmc.fieldState.adbCommandString,
      onValueChange = {
        runVmc.fieldState.adbCommandString = it
      },
      label = { Text("Command") },
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next
      ),
      singleLine = true
    )

    Row(Modifier.padding(vertical = 10.dp)) {
      Row(Modifier.weight(1f)) {
        formActions()
      }
      Row {
        Button(modifier = Modifier.padding(end = 10.dp),
          onClick = {
            focusManager.clearFocus()
            runVmc.runAdbCommand()
          }) {
          Text("RUN")
        }
        Button(onClick = {
          focusManager.clearFocus()
          runVmc.destroyAdbProcess()
        }) {
          Text("KILL")
        }
      }
    }

    // doitwrong
    OutlinedTextField(
      modifier = Modifier.fillMaxWidth(),
      value = runVmc.adbCommandOutput.value,
      onValueChange = { },
      readOnly = true,
      placeholder = {
        Text(
          "output from:\n  adb connect \$host\n  adb -s \$host \$command",
          fontStyle = FontStyle.Italic
        )
      },
      minLines = 3
    )
  }
}