package com.voltacontrol.bluetooth.demo.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BluetoothConnection(
    bluetoothOn: Boolean,
    onBluetoothStatusChange: () -> Unit
) {

    if (!bluetoothOn)
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
                Switch(checked = bluetoothOn, onCheckedChange = { onBluetoothStatusChange() })
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
                    .background(color = Color.Gray)
            ) {}
        }


}




