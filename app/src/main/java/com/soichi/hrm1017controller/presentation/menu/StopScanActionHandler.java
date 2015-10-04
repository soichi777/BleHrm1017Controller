package com.soichi.hrm1017controller.presentation.menu;

import com.soichi.lib.ble.BleWrapper;

import android.content.Context;

public class StopScanActionHandler implements OptionsItemActionHandler {
	@Override
	public boolean handle(Context context, BleWrapper bleWrapper) {
		bleWrapper.stopScanning();
		return true;
	}
}
