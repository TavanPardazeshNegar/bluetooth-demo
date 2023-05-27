package com.voltacontrol.bluetooth.demo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.voltacontrol.bluetooth.demo.ui.pages.BluetoothConnection
import com.voltacontrol.bluetooth.demo.ui.theme.BluetoothDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                    onDeviceSelect = { device -> createConnection(device) })
            }
        }
    }

    private fun createConnection(_device: BluetoothDevice) {
        if (!checkBluetoothPermission()) {
            requestPermissionBluetooth()
            return
        }

        val device = bluetoothAdapter.getRemoteDevice(_device.address)

        val socket = try {
            val method = device::class.java.getMethod("createRfcommSocketToServiceRecord")
            method.isAccessible = true
            method.invoke(device, UUID.randomUUID()) as BluetoothSocket
        } catch (e: Exception) {
            device.createRfcommSocketToServiceRecord(UUID.randomUUID())
        }

        CoroutineScope(IO).launch {
            try {
                socket.connect()
                App.socket = socket
                withContext(Main) {
                    openCommunicationPage()
                }
            } catch (e: Exception) {
                Log.e("BCAct", e.message ?: "")
            }
        }

    }

    private fun manageMyConnectedSocket(socket: BluetoothSocket) {
        App.socket = socket
        //withContext(Main) {
        openCommunicationPage()
        //}
    }

    private fun openCommunicationPage() {
        val intent = Intent(this, BluetoothCommunicationActivity::class.java)
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                Manifest.permission.BLUETOOTH_CONNECT
            else
                Manifest.permission.BLUETOOTH
        ) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestPermissionBluetooth() {
        bluetoothPermissionLauncher.launch(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            else
                arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
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