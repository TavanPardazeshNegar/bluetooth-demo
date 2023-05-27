package com.voltacontrol.bluetooth.demo.ui.pages

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData

@SuppressLint("MissingPermission")
@Composable
fun BluetoothConnection(
    bluetoothIsOn: MutableLiveData<Boolean>,
    pairedList: Set<BluetoothDevice>?,
    onBluetoothStatusChange: () -> Unit,
    onDeviceSelect:(device:BluetoothDevice) -> Unit
) {

    val bluetoothOn by bluetoothIsOn.observeAsState()

    if (bluetoothOn == false)
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "BluetoothIsOff")
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = bluetoothOn == true,
                    onCheckedChange = { onBluetoothStatusChange() })
            }
        }
    else
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Please Select a Device")
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Gray.copy(alpha = 0.2f))
            ) {
                if (pairedList.isNullOrEmpty())
                    item {
                        Text(
                            modifier = Modifier
                                .padding(16.dp),
                            style = TextStyle.Default.copy(textAlign = TextAlign.Center),
                            text = "not available any paired device, please first pair some device`s from your phone bluetooth page"
                        )
                    }
                else
                    items(items = pairedList.toList()) { device ->
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onDeviceSelect(device) },
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = device.name ?: "", style = MaterialTheme.typography.h6)
                            Text(
                                text = device.address ?: "",
                                style = MaterialTheme.typography.caption
                            )
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black)
                                    .height(1.dp)
                                    .padding(vertical = 4.dp)
                            )
                        }
                    }
            }
        }


}




