package menu;

import com.soichi.ble.lib.BleWrapper;

import android.content.Context;

public class StopScanActionHandler implements OptionsItemActionHandler {
	@Override
	public boolean handle(Context context, BleWrapper bleWrapper) {
		bleWrapper.stopScanning();
		return true;
	}
}
