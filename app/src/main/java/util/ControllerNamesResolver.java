package util;

import java.util.HashMap;
import com.soichi.ble.lib.BleNamesResolver;

public class ControllerNamesResolver extends BleNamesResolver {
	
	/*
	 * wish to extend BleNamesResolver and to resolve both ble-defined names and other names 
	 */
	private static HashMap<String, String> mServices = new HashMap<String, String>();
	
	static public String ResolveServiceName(final String uuid) {
		String result = mServices.get(uuid);
		if (result == null) result = "Unknown Service";
		return result;
	}

	static {
		mServices.put(ControllerUUID.Service.CONTROLLER.toString(), "Android controller");
	}
}
