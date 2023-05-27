package com.voltacontrol.bluetooth.demo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
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
import androidx.lifecycle.MutableLiveData
import com.voltacontrol.bluetooth.demo.ui.pages.BluetoothConnection
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme
import java.util.*

class BluetoothConnectionActivity : ComponentActivity() {

    private val bluetoothIsOn = MutableLiveData(false)
    private val pairedList = mutableStateOf<Set<BluetoothDevice>?>(null)

    private lateinit var bluetoothPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentUI()

        observeData()

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

    private fun observeData() {
        bluetoothIsOn.observe(this) { isOn ->
            if (isOn == true)
                setPairedList()
        }
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
                    bluetoothIsOn = bluetoothIsOn,
                    pairedList = pairedList.value,
                    onBluetoothStatusChange = { turnOnBluetooth() },
                    onDeviceSelect = { device -> createSocket(device) })
            }
        }
    }

    private fun createSocket(device: BluetoothDevice) {
        if (!checkBluetoothPermission()) {
            requestPermissionBluetooth()
            return
        }

        val socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID())

        App.socket = socket

        openCommunicationPage()
    }

    private fun openCommunicationPage() {
        val intent = Intent(this, BluetoothConnectionActivity::class.java)
        startActivity(intent)
    }

    private fun turnOnBluetooth() {
        val enableBlueToothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (checkBluetoothPermission()) {
            startActivityForResult(enableBlueToothIntent, 2)
        } else
            requestPermissionBluetooth()
    }

    private fun checkBluetoothPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionBluetooth() {
        bluetoothPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_CONNECT,
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        bluetoothIsOn.value = resultCode == RESULT_OK
    }

    private fun setPairedList() {
        if (checkBluetoothPermission())
            pairedList.value = bluetoothAdapter.bondedDevices
        else
            requestPermissionBluetooth()
    }

}