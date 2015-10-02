package menu;

import com.soichi.ble.lib.BleWrapper;

import android.content.Context;

public class UnknownActionHandler implements OptionsItemActionHandler {
	@Override
	public boolean handle(Context context, BleWrapper wrapper) {
		return false;
	}
}
