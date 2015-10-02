package util;

import android.os.Build;

public class Debug {
	
	/**
	 * detects if the testing environment is an emulator rather than real
	 * devices. Since emulators do not support BLE, running Bluetooth support
	 * check disables the whole application.
	 * 
	 * TODO startScan menu generates NullPointerException presumably because mBleWrapper is not ready
	 * @return boolean
	 */
	public static boolean isEmulator() {
		return Build.FINGERPRINT.startsWith("generic");
	}

}
