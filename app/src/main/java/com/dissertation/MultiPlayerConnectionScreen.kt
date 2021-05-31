/* This class contains the code used for displaying the MultiPlayerConnection Screen and the Bluetooth setup within the screen. */
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

    /*
    When the class is called, display the Multiplayer Connection Screen. Store multiple components
    from this screen as variables to be used elsewhere in this class.

    Create a Bluetooth Adapter to determine if the current device has Bluetooth capabilities. If the
    device has Bluetooth, call the Bluetooth Setup function.

    If Bluetooth is supported, control and toggle Bluetooth from within the application using the
    handleBluetoothToggle function. Start Discovery for the device and find devices that the current
    device is already paired with.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.multi_player_connection_screen)

        bluetoothButton = findViewById(R.id.multiPlayerConnectionScreenBluetoothButton)
        bluetoothToggleSwitch = findViewById(R.id.bluetoothToggleSwitch)
        pairedDevicesListView = findViewById(R.id.pairedDevicesListView)
        newDevicesListView = findViewById(R.id.newDevicesListView)
        progressBar = findViewById(R.id.bluetoothProgressBar)

        val statusChangedFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            bluetoothIsSupported = true
            bluetoothSetup()
        } else {
            bluetoothIsSupported = false
        }

        if (bluetoothIsSupported) {
            registerReceiver(statusChangedReceiver, statusChangedFilter)
            bluetoothToggleSwitch?.setOnClickListener {
                handleBluetoothToggle()
            }
            startDeviceDiscovery()
            findPairedDevices()
        }
    }

    /*
    Determine the state of Bluetooth on the device; whether it is ON or OFF or if the state has
    changed.
     */
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

    /*
    Scan for other nearby devices. Change the visibility of the current device.

    If a new device is found, get the device details and add them to a map of found devices.
     */
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

    /*
    Determine if Bluetooth is already switched ON on the device. If it is, update the screen to
    reflect the Bluetooth status. If it is not, display a prompt on the screen asking the user if
    they would like to turn Bluetooth on.

    Begin the scan for new devices.
     */
    private fun bluetoothSetup() {
        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        } else if (bluetoothAdapter?.isEnabled == true) {
            bluetoothOnDisplaySettings()
        }
        startDeviceDiscovery()
    }

    /*
    When the toggle button within the screen is changed, depending on the current status of the
    Bluetooth, either enable or disable Bluetooth accordingly.
     */
    private fun handleBluetoothToggle() {
        if (bluetoothToggleSwitch?.isChecked == true) {
            bluetoothAdapter?.enable()
        }  else if (bluetoothToggleSwitch?.isChecked == false) {
            bluetoothAdapter?.disable()
        }
    }

    /*
    Using the Bluetooth settings of the current device, return a list of devices that are currently
    paired with this device. Store this list in a local variable and for each device in the list,
    store the name and MAC address and add the device to a list of paired devices called "pairs".

    Add all of the keys of the "pairs" list, i.e the names of the paired devices, to a new ArrayList.
    Using an Array Adapter, convert the ArrayList into a list that can be displayed on screen.
     */
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

    /*
    Begin the device scan for new Bluetooth devices within the proximity using the Bluetooth Adapter
    and Broadcast Receiver.
     */
    private fun startDeviceDiscovery() {
        val devicesFoundFilter = IntentFilter()
        devicesFoundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
        devicesFoundFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        devicesFoundFilter.addAction(BluetoothDevice.ACTION_FOUND)

        registerReceiver(devicesFoundReceiver, devicesFoundFilter)
        bluetoothAdapter?.startDiscovery()
    }

    /*
    If the list of new devices if not empty, convert the names of the new devices into an ArrayList.
    Using an Array Adapter, convert the ArrayList into a list that can be displayed on screen.
     */
    private fun displayFoundDevices(foundDevices: MutableMap<String?, String?>) {
        val newDeviceNames = if (foundDevices.isNotEmpty()) {
            ArrayList(foundDevices.keys)
        } else {
            arrayListOf("No Devices Found.")
        }

        val adapter = ArrayAdapter(this, R.layout.list_view_item, newDeviceNames)
        newDevicesListView?.adapter = adapter
    }

    /*
    When the Bluetooth is OFF, change the on-screen elements to reflect this. The toggle switch
    will be in the "off" position and the Bluetooth logo will appear grey.
     */
    private fun bluetoothOffDisplaySettings() {
        bluetoothButton?.alpha = 0.5f
        bluetoothToggleSwitch?.isChecked = false
        pairedDevicesListView?.visibility = View.GONE
        bluetoothAdapter?.cancelDiscovery()
    }

    /*
    When the Bluetooth is ON, change the on-screen elements to reflect this. The toggle switch
    will be in the "on" position and the Bluetooth logo will appear black.
    */
    private fun bluetoothOnDisplaySettings() {
        bluetoothButton?.alpha = 1.0f
        bluetoothToggleSwitch?.isChecked = true
        pairedDevicesListView?.visibility = View.VISIBLE
        startDeviceDiscovery()
        findPairedDevices()
    }
}