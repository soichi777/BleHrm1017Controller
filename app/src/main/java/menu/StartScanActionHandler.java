package menu;

import com.soichi.ble.lib.BleWrapper;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;
import util.Debug;

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
