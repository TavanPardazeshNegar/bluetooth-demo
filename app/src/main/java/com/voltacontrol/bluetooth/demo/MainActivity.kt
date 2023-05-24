package com.voltacontrol.bluetooth.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.voltacontrol.bluetooth.demo.ui.pages.MainUI
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme

class MainActivity : ComponentActivity() {

    private val currentStatus = mutableStateOf(Statuses.Start)
    private val indicatorColor = mutableStateOf(Color.Blue)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BluetoothDemoTheme {
                // A surface container using the 'background' color from the theme
                MainUI(
                    indicatorColor = indicatorColor.value,
                    status = currentStatus.value,
                    hashTagInput = "#AS",
                    onBalanceClick = { onBalanceClick() },
                    onStatusClick = { onStatusChange() },
                )
            }
        }
    }

    private fun onBalanceClick() {
        indicatorColor.value = when (indicatorColor.value) {
            Color.Red -> Color.Blue
            else -> Color.Red
        }
    }

    private fun onStatusChange() {
        currentStatus.value = when (currentStatus.value) {
            Statuses.Start -> Statuses.Stop
            Statuses.Stop -> Statuses.Start
        }
    }
}

enum class Statuses {
    Start,
    Stop
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BluetoothDemoTheme {
        Greeting("Android")
    }
}