package menu;

import com.soichi.ble.lib.BleWrapper;

import android.content.Context;

public interface OptionsItemActionHandler {
	public boolean handle(Context context, BleWrapper wrapper);
}
