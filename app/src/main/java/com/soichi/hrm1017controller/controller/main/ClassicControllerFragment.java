package com.soichi.hrm1017controller.controller.main;

import com.soichi.hrm1017controller.R;
import com.soichi.lib.ble.BleWrapper;
import com.soichi.hrm1017controller.util.Debug;

import android.app.Fragment;
import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.soichi.hrm1017controller.util.ControllerUUID;

import android.view.ViewGroup;

public class ClassicControllerFragment extends Fragment {

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
                if (Debug.isEmulator() || mBleWrapper == null) {
                    Log.d("ble", "clicking");
                    return true;
                }

                BluetoothGattCharacteristic c = mBleWrapper.getGatt().getService(ControllerUUID.Service.CONTROLLER)
                        .getCharacteristic(ControllerUUID.Characteristic.CONTROLLER);

                //mBleWrapper.getCharacteristicsForService(mBleWrapper.getGatt().getService(ControllerUUID.Service.LOG));

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mBleWrapper.writeDataToCharacteristic(c, new byte[]{0x01});
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mBleWrapper.writeDataToCharacteristic(c, new byte[]{0x02});
                    return true;
                }
                return false;
            }
        };

        OnTouchListener rightTurnButtonTouchListener = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (Debug.isEmulator() || mBleWrapper == null) {
                    Log.d("ble", "clicking");
                    return true;
                }

                BluetoothGattCharacteristic c = mBleWrapper.getGatt().getService(ControllerUUID.Service.CONTROLLER)
                        .getCharacteristic(ControllerUUID.Characteristic.CONTROLLER);

                //mBleWrapper.getCharacteristicsForService(mBleWrapper.getGatt().getService(ControllerUUID.Service.LOG));

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mBleWrapper.writeDataToCharacteristic(c, new byte[]{0x03});
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mBleWrapper.writeDataToCharacteristic(c, new byte[]{0x04});
                    return true;
                }
                return false;
            }
        };

        OnTouchListener moveForwardButtonTouchListener = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                BluetoothGattCharacteristic c = mBleWrapper.getGatt().getService(ControllerUUID.Service.CONTROLLER)
                        .getCharacteristic(ControllerUUID.Characteristic.CONTROLLER);

                //mBleWrapper.getCharacteristicsForService(mBleWrapper.getGatt().getService(ControllerUUID.Service.LOG));

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    mBleWrapper.writeDataToCharacteristic(c, new byte[]{0x05});
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mBleWrapper.writeDataToCharacteristic(c, new byte[]{0x06});
                    return true;
                }
                return false;
            }
        };

        View view = inflator.inflate(R.layout.fragment_controller, container, false);
        view.findViewById(R.id.btn_left).setOnTouchListener(leftTurnButtonTouchListener);
        view.findViewById(R.id.btn_right).setOnTouchListener(rightTurnButtonTouchListener);
        view.findViewById(R.id.btn_forward).setOnTouchListener(moveForwardButtonTouchListener);

        return view;
    }

    /**
     * sets BleWrapper object
     *
     * @param wrapper
     */
    public void setBleWrapper(BleWrapper wrapper) {
        mBleWrapper = wrapper;
    }

}
