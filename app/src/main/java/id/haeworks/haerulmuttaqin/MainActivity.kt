package id.haeworks.haerulmuttaqin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.haeworks.core.ui.theme.HaerulMuttaqinTheme
import id.haeworks.haerulmuttaqin.ui.SourceViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            HaerulMuttaqinTheme {
                val vm: SourceViewModel = hiltViewModel()
                val uiState by vm.uiState.collectAsState()

                val query = remember { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { paddingValues ->
                    Column(
                        modifier = Modifier.padding(paddingValues).padding(16.dp)
                    ) {
                        Spacer(modifier = Modifier.padding(16.dp))
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = query.value,
                            onValueChange = {
                                query.value = it
                            }
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                vm.getChar(query.value)
                            }
                        ) {
                            Text("Go")
                        }
                        Spacer(modifier = Modifier.padding(16.dp))
                        Text(
                            text = uiState.allSource.toString(),
                            modifier = Modifier.fillMaxWidth()
                        )
                        HorizontalDivider()
                        Text(
                            text = uiState.containsSource.toString(),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }



//                NavHost(
//                    navController = navController,
//                    startDestination = ListRoute,
//                    enterTransition = { fadeIn(animationSpec = tween(250)) },
//                    exitTransition = { fadeOut(animationSpec = tween(250)) },
//                    popEnterTransition = { fadeIn(animationSpec = tween(250)) },
//                    popExitTransition = { fadeOut(animationSpec = tween(250)) },
//                ) {
//                    // Show the prepopulated data somewhere simple
//                    listRoute(navController)
//                    detailRoute(navController)
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HaerulMuttaqinTheme {
        Greeting("Android")
    }
}