package com.voltacontrol.bluetooth.demo

import android.app.Application
import android.bluetooth.BluetoothSocket

class App:Application() {

    companion object{
        var socket:BluetoothSocket? = null
    }

}