package com.soichi.hrm1017controller.presentation.menu;

import com.soichi.lib.ble.BleWrapper;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import com.soichi.hrm1017controller.util.Debug;

public class StartScanActionHandler implements OptionsItemActionHandler {
	
	@Override
	public boolean handle(Context context, BleWrapper bleWrapper) {
		if (Debug.isEmulator()) {
			Toast.makeText(context, "testing on emulator. BLE is not available", Toast.LENGTH_SHORT).show();
			((Activity) context).finish();
		}
		
		bleWrapper.startScanning();
		return true;
	}

}
