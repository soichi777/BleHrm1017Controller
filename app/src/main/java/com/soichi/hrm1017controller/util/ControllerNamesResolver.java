package com.soichi.hrm1017controller.util;

import android.util.Log;

import java.util.HashMap;
import com.soichi.lib.ble.BleNamesResolver;

public class ControllerNamesResolver extends BleNamesResolver {
	
	/*
	 * wish to extend BleNamesResolver and to resolve both ble-defined names and other names 
	 */
	private static HashMap<String, String> mServices = new HashMap<String, String>();
	
	static public String resolveServiceName(final String uuid) {
		String result = mServices.get(uuid);
		if (result == null) result = "Unknown Service";
		return result;
	}

	static {
		mServices.put(ControllerUUID.Service.CONTROLLER.toString(), "Android controller");
		mServices.put(ControllerUUID.Service.LOG.toString(), "Action log");
	}
}
