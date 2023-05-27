package com.voltacontrol.bluetooth.demo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.voltacontrol.bluetooth.demo.ui.pages.BluetoothCommunication
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val currentStatus = mutableStateOf(Statuses.Start)
    private val indicatorColor = mutableStateOf(Color.Blue)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentUI()

        val bluetoothManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(BluetoothManager::class.java)
        } else {
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        }

        bluetoothAdapter = bluetoothManager.adapter

    }

    override fun onResume() {
        super.onResume()

        if(!bluetoothAdapter.isEnabled){

        }

    }

    private fun setContentUI() {
        setContent {
            BluetoothDemoTheme {
                // A surface container using the 'background' color from the theme
                BluetoothCommunication(
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