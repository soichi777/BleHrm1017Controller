package com.soichi.hrm1017controller.presentation.menuhandler;

import com.soichi.lib.ble.BleWrapper;

import android.content.Context;

public class UnknownActionHandler implements OptionsItemActionHandler {
	@Override
	public boolean handle(Context context, BleWrapper wrapper) {
		return false;
	}
}
