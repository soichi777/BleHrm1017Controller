package com.soichi.hrm1017controller.presentation;

import android.app.Activity;

import java.util.List;

import com.soichi.hrm1017controller.R;
import com.soichi.lib.ble.BleWrapper;
import com.soichi.lib.ble.BleWrapperUiCallbacks;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.soichi.hrm1017controller.util.ControllerNamesResolver;
import com.soichi.hrm1017controller.util.Debug;

public class MainActivity extends Activity {

	// TODO move this definition to Debug class
	private static final String LOGTAG = "BLETEST";
	private static final boolean DEBUG = true;

	private BleWrapper mBleWrapper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mBleWrapper = new BleWrapper(this, new BleWrapperUiCallbacks.Null() {

			@Override
			public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
				String msg = "uiDeviceFound: " + device.getName() + ", " + rssi + ", " + record.toString();
				// TODO "MainActivity.this" may be wrong???
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

				if (DEBUG) {
					Log.d(LOGTAG, msg);
				}

				connect(device);
			}

			@Override
			public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device,
					List<BluetoothGattService> services) {

				BluetoothGattCharacteristic c;

				for (BluetoothGattService service : services) {
					// String serviceName =
					// BleNamesResolver.resolveUuid(service.getUuid().toString());
					// TODO
					String serviceName = ControllerNamesResolver.resolveUuid(service.getUuid().toString());

					if (DEBUG) {
						Log.d(LOGTAG, "service uuid is: " + service.getUuid() + " " + serviceName);
					}

					mBleWrapper.getCharacteristicsForService(service);
				}

				// mState = mSensorState.ACC_ENABLE;
			}

			@Override
			public void uiNewValueForCharacteristic(BluetoothGatt gatt, BluetoothDevice device,
					BluetoothGattService service, BluetoothGattCharacteristic ch, String strValue, int intValue,
					byte[] rawValue, String timestamp) {
				super.uiNewValueForCharacteristic(gatt, device, service, ch, strValue, intValue, rawValue, timestamp);

				if (DEBUG)
					Log.d(LOGTAG, "uiNewValueForCharacteristic");

				for (byte b : rawValue) {
					if (DEBUG) {
						Log.d(LOGTAG, "Val: " + b);
					}
				}

			}
		});

		if (!Debug.isEmulator() && mBleWrapper.checkBleHardwareAvailable() == false) {
			Toast.makeText(this, "No BLE-compatible hardware detected", Toast.LENGTH_SHORT).show();
			finish();
		}

		if (savedInstanceState == null) {
			final ControllerFragment ctrl = new ControllerFragment();
			ctrl.initialize(mBleWrapper);
			getFragmentManager().beginTransaction().add(R.id.container, ctrl).commit();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (!Debug.isEmulator() && mBleWrapper.isBtEnabled() == false) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(enableBtIntent);
			finish();
		}
		Boolean result = mBleWrapper.initialize();
		if (result == false) {
			Log.d(LOGTAG, "error occured in onResume");
			finish();
		}

		if (mBleWrapper.isConnected()) {
			return;
		}
		connect(mBleWrapper.getDevice());
	}

	@Override
	protected void onPause() {
		super.onPause();
		mBleWrapper.diconnect();
		mBleWrapper.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 
	 * @param BluetoothDevice
	 * @return boolean
	 */
	private boolean connect(final BluetoothDevice device) {
		boolean status = false;
		
		if (device == null) {
			return status;
		}
		
		if (device.getName().equals("mbed AI robot") == true) {
			status = mBleWrapper.connect(device.getAddress().toString());
			if (status == false) {
				if (DEBUG) {
					Log.d(LOGTAG, "uiDeviceFound: Connection problem");
				}
			}
		}
		return status;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		MainActivityOptionsItemAction action = MainActivityOptionsItemAction.valueOf(item);

		return action.getHandler().handle(this, mBleWrapper) || super.onOptionsItemSelected(item);
	}

}
