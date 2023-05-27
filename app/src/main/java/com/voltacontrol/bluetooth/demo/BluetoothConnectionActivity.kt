package com.voltacontrol.bluetooth.demo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.voltacontrol.bluetooth.demo.ui.pages.BluetoothConnection
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme

class BluetoothConnectionActivity : ComponentActivity() {

    private lateinit var bluetoothPermissionLauncher: ActivityResultLauncher<Array<String>>
    private val bluetoothIsOn = mutableStateOf(false)

    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentUI()

        bluetoothPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
                bluetoothPermissionRequestResult(result)
            }

        val bluetoothManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getSystemService(BluetoothManager::class.java)
        } else {
            getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        }
        bluetoothAdapter = bluetoothManager.adapter

    }

    private fun bluetoothPermissionRequestResult(result: Map<String, @JvmSuppressWildcards Boolean>) {

    }

    override fun onResume() {
        super.onResume()

        bluetoothIsOn.value = bluetoothAdapter.isEnabled

    }


    private fun setContentUI() {
        setContent {
            BluetoothDemoTheme {
                BluetoothConnection(
                    bluetoothOn = bluetoothIsOn.value,
                    onBluetoothStatusChange = { turnOnBluetooth() }
                )
            }
        }
    }

    private fun turnOnBluetooth() {
        val enableBlueToothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBlueToothIntent, 2)
        } else
            requestPermissionBluetooth()
    }

    private fun requestPermissionBluetooth() {
        bluetoothPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        bluetoothIsOn.value = resultCode == RESULT_OK
    }

}