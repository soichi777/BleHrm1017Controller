package com.soichi.hrm1017controller.presentation.menuhandler.main;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.widget.Toast;

import com.soichi.hrm1017controller.presentation.menuhandler.OptionsItemActionHandler;
import com.soichi.hrm1017controller.util.ControllerUUID;
import com.soichi.hrm1017controller.util.Debug;
import com.soichi.lib.ble.BleWrapper;

public class StartLogOutputActionHandler implements OptionsItemActionHandler {
	
	@Override
	public boolean handle(Context context, BleWrapper bleWrapper) {
		if (Debug.isEmulator()) {
			Toast.makeText(context, "testing on emulator. BLE is not available", Toast.LENGTH_SHORT).show();
			((Activity) context).finish();
		}

		// TODO have uses choose to show log outputs
		if (true) {
			BluetoothGatt gatt = bleWrapper.getGatt();
			BluetoothGattCharacteristic bc = gatt.getService(ControllerUUID.Service.LOG)
					.getCharacteristic(ControllerUUID.Characteristic.ACTION_LOG);
            bleWrapper.requestCharacteristicValue(bc);
		}
		return true;
	}

}
