package menu;

import com.soichi.ble.lib.BleWrapper;
import com.soichi.blehrm1017controller.R;
import com.soichi.blehrm1017controller.R.string;

import android.content.Context;
import android.widget.Toast;

public class HogeActionHandler implements OptionsItemActionHandler {
	
	@Override
	public boolean handle(Context context, BleWrapper wrapper) {
		Toast.makeText(context.getApplicationContext(), R.string.hello_world, Toast.LENGTH_SHORT).show();
		return true;
	}

}
