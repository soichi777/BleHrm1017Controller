package com.soichi.hrm1017controller.presentation.menu;

import com.soichi.lib.ble.BleWrapper;
import com.soichi.hrm1017controller.R;

import android.content.Context;
import android.widget.Toast;

public class HogeActionHandler implements OptionsItemActionHandler {
	
	@Override
	public boolean handle(Context context, BleWrapper wrapper) {
		Toast.makeText(context.getApplicationContext(), R.string.hello_world, Toast.LENGTH_SHORT).show();
		return true;
	}

}
