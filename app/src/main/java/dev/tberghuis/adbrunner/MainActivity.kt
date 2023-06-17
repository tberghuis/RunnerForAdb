package dev.tberghuis.adbrunner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.tberghuis.adbrunner.ui.HomeScreen
import dev.tberghuis.adbrunner.ui.NewScreen
import dev.tberghuis.adbrunner.ui.RunScreen
import dev.tberghuis.adbrunner.ui.theme.ADBRunnerTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      ADBRunnerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          MyAppNavHost()
        }
      }
    }
  }
}

@Composable
fun MyAppNavHost(
) {
  val navController = rememberNavController()
  NavHost(
    navController = navController, startDestination = "home"
  ) {
    composable("home") {
      HomeScreen(
        navigateNewScreen = { navController.navigate("new_screen") },
        navigateRunScreen = { id -> navController.navigate("run_screen/$id") },
      )
    }
    composable("new_screen") {
      NewScreen(
        popHome = {
          navController.popBackStack()
        }
      )
    }

    composable(
      "run_screen/{id}",
      arguments = listOf(navArgument("id") { type = NavType.IntType })
    ) {
      RunScreen(
        popHome = {
          navController.popBackStack()
        }
      )
    }


  }
}