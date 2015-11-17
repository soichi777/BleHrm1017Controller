package com.soichi.hrm1017controller.presentation.menu;

import com.soichi.lib.ble.BleWrapper;

import android.content.Context;

public interface OptionsItemActionHandler {
	boolean handle(Context context, BleWrapper wrapper);
}
