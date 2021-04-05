package com.dissertation

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


private const val REQUEST_ENABLE_BT = 0

class MultiPlayerConnectionScreen : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var bluetoothToggleSwitch : Switch? = null

    private var bluetoothIsSupported : Boolean = false
    private var bluetoothAdapter : BluetoothAdapter? = null
    private var bluetoothButton : ImageButton? = null
    private var pairedDevicesListView : ListView? = null
    private var newDevicesListView : ListView? = null
    private var progressBar : ProgressBar? = null
    private var foundDevices : MutableMap<String?, String?> = mutableMapOf()
    private var pairs : MutableMap<String?, String?> = mutableMapOf()
    private var devicesNames : ArrayList<String?> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_player_connection_screen)

        // Initialise components
        bluetoothButton = findViewById(R.id.multiPlayerConnectionScreenBluetoothButton)
        bluetoothToggleSwitch = findViewById(R.id.bluetoothToggleSwitch)
        pairedDevicesListView = findViewById(R.id.pairedDevicesListView)
        newDevicesListView = findViewById(R.id.newDevicesListView)
        progressBar = findViewById(R.id.bluetoothProgressBar)

        val statusChangedFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)

        // Initialize bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            bluetoothIsSupported = true
            bluetoothSetup()
        } else {
            // bluetooth not supported
            bluetoothIsSupported = false
        }

        if (bluetoothIsSupported) {
            // Status Changed
            registerReceiver(statusChangedReceiver, statusChangedFilter)
            // Toggle Bluetooth using Switch
            bluetoothToggleSwitch?.setOnClickListener {
                handleBluetoothToggle()
            }
            // Show Paired Devices
//            startDeviceDiscovery()
            findPairedDevices()
        }
    }

    private val statusChangedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val action = intent.action
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val state = intent.getIntExtra(
                    BluetoothAdapter.EXTRA_STATE,
                    BluetoothAdapter.ERROR
                )
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        bluetoothOffDisplaySettings()
                    }
                    BluetoothAdapter.STATE_ON -> {
                        bluetoothOnDisplaySettings()
                    }
                }
            }
        }
    }

    private val devicesFoundReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when(intent.action) {
                BluetoothAdapter.ACTION_DISCOVERY_STARTED -> {
                    progressBar?.visibility = View.VISIBLE
                }
                BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                    progressBar?.visibility = View.GONE
                }
                BluetoothDevice.ACTION_FOUND -> {
                    println("DEVICE FOUND")
                    val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address

                    if (!foundDevices.containsKey(deviceName) && !foundDevices.containsValue(deviceHardwareAddress)) {
                        foundDevices[deviceName] = deviceHardwareAddress
                    }
                    displayFoundDevices(foundDevices)
                }
            }
        }
    }

    private fun bluetoothSetup() {
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else if (bluetoothAdapter?.isEnabled == true) {
            bluetoothOnDisplaySettings()
        }
        startDeviceDiscovery()
    }

    private fun handleBluetoothToggle() {
        if (bluetoothToggleSwitch?.isChecked == true) {
            bluetoothAdapter?.enable()
        }  else if (bluetoothToggleSwitch?.isChecked == false) {
            bluetoothAdapter?.disable()
        }
    }

    private fun findPairedDevices() {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            val deviceHardwareAddress = device.address // MAC address
            pairs[deviceName] = deviceHardwareAddress
        }

        devicesNames = ArrayList(pairs.keys)

        val adapter = ArrayAdapter(this, R.layout.list_view_item, devicesNames)
        pairedDevicesListView?.adapter = adapter
    }

    private fun startDeviceDiscovery() {
        println("HEREeeeee")
        val devicesFoundFilter = IntentFilter()
        devicesFoundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        devicesFoundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        devicesFoundFilter.addAction(BluetoothDevice.ACTION_FOUND)

        registerReceiver(devicesFoundReceiver, devicesFoundFilter)
        bluetoothAdapter?.startDiscovery()
    }

    private fun displayFoundDevices(foundDevices: MutableMap<String?, String?>) {
        val newDeviceNames = if (foundDevices.isNotEmpty()) {
            ArrayList(foundDevices.keys)
        } else {
            arrayListOf("No Devices Found.")
        }

        println("NEW DEVICES: $newDeviceNames")
        val adapter = ArrayAdapter(this, R.layout.list_view_item, newDeviceNames)
        newDevicesListView?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()

        bluetoothAdapter?.cancelDiscovery()
        unregisterReceiver(statusChangedReceiver)
        unregisterReceiver(devicesFoundReceiver)
    }

    private fun bluetoothOffDisplaySettings() {
        bluetoothButton?.alpha = 0.5f
        bluetoothToggleSwitch?.isChecked = false
        pairedDevicesListView?.visibility = View.GONE
        bluetoothAdapter?.cancelDiscovery()
    }

    private fun bluetoothOnDisplaySettings() {
        bluetoothButton?.alpha = 1.0f
        bluetoothToggleSwitch?.isChecked = true
        pairedDevicesListView?.visibility = View.VISIBLE
        startDeviceDiscovery()
        findPairedDevices()
    }
}