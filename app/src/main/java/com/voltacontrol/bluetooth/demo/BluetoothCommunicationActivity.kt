package com.voltacontrol.bluetooth.demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.voltacontrol.bluetooth.demo.ui.pages.BluetoothCommunication
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BluetoothCommunicationActivity : ComponentActivity() {

    private val currentStatus = mutableStateOf(Statuses.Start)
    private val indicatorColor = mutableStateOf(Color.Blue)
    private val hashTagInput = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentUI()
        readeInputData()

    }

    private fun parseNewData(data: String) {
        if (data.startsWith("#"))
            hashTagInput.value = data
        else if (data == "Blue") {
            indicatorColor.value = Color.Blue
        } else if (data == "Red") {
            indicatorColor.value = Color.Red
        } else
            Toast.makeText(this, "invalid data: ${data}", Toast.LENGTH_SHORT).show()
    }

    private fun setContentUI() {
        setContent {
            BluetoothDemoTheme {
                // A surface container using the 'background' color from the theme
                BluetoothCommunication(
                    indicatorColor = indicatorColor.value,
                    status = currentStatus.value,
                    hashTagInput = hashTagInput.value,
                    onBalanceClick = { onBalanceClick() },
                    onStatusClick = { onStatusChange() },
                )
            }
        }
    }

    private fun onBalanceClick() {
        sendData("Balance")
    }

    private fun onStatusChange() {
        currentStatus.value = when (currentStatus.value) {
            Statuses.Start -> {
                sendData("Start")
                Statuses.Stop
            }
            Statuses.Stop -> {
                sendData("Stop")
                Statuses.Start
            }
        }
    }

    private fun sendData(data: String) {
        CoroutineScope(IO).launch {
            try {
                App.socket?.outputStream?.write(data.toByteArray())
            } catch (e: Exception) {
                Toast.makeText(
                    this@BluetoothCommunicationActivity,
                    "error on sending data to device",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun readeInputData() {
        CoroutineScope(IO).launch {
            val buffer = ByteArray(1024)
            var bytes = 0
            while (true) {
                try {
                    bytes = 0
                    delay(100)
                    bytes = App.socket?.inputStream?.available() ?: 0
                    bytes =
                        App.socket?.inputStream?.read(buffer, 0, if (bytes <= 1023) bytes else 1023)
                            ?: 0
                    withContext(Main) {
                        parseNewData(String(buffer, 0, bytes))
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@BluetoothCommunicationActivity,
                        "error on reading data from socket:${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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