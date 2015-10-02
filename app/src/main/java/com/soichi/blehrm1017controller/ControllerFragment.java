package com.soichi.blehrm1017controller;

import com.soichi.ble.lib.BleWrapper;
import com.soichi.blehrm1017controller.R;

import android.app.Fragment;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import util.ControllerUUID;
import android.view.ViewGroup;

public class ControllerFragment extends Fragment {

	private static final String LOGTAG = "BLETEST";

	private BleWrapper mBleWrapper = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

		OnTouchListener leftTurnButtonTouchListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				BluetoothGattCharacteristic c = mBleWrapper.getGatt().getService(ControllerUUID.Service.CONTROLLER)
						.getCharacteristic(ControllerUUID.Characteristic.CONTROLLER);

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mBleWrapper.writeDataToCharacteristic(c, new byte[] { 0x01 });
					return true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mBleWrapper.writeDataToCharacteristic(c, new byte[] { 0x02 });
					return true;
				}
				return false;
			}
		};

		View view = inflator.inflate(R.layout.fragment_controller, container, false);
		//view.findViewById(R.id.right_turn_button).setOnClickListener(rightTurnButtonClickListener);
		view.findViewById(R.id.left_turn_button).setOnTouchListener(leftTurnButtonTouchListener);

		return view;
	}

	/**
	 * sets BleWrapper object
	 * @param BleWrapper
	 */
	public void initialize(BleWrapper wrapper) {
		mBleWrapper = wrapper;
	}
}
