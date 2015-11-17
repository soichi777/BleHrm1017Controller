package com.soichi.hrm1017controller.util;

import java.util.UUID;

public class ControllerUUID {
	
	public static class Service {
		final static public UUID CONTROLLER = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
		final static public UUID LOG = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");
	}
	
	public static class Characteristic {
		final static public UUID CONTROLLER = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
		final static public UUID ACTION_LOG = UUID.fromString("0000fff3-0000-1000-8000-00805f9b34fb");
	}
	
	public static class Descriptor {
		
	}
}
