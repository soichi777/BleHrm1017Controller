package com.soichi.hrm1017controller.controller.main;

import java.util.List;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.soichi.hrm1017controller.R;
import com.soichi.hrm1017controller.presentation.layout.main.ClassicControllerLayout;
import com.soichi.hrm1017controller.presentation.layout.main.InteractiveMonitorLayout;
import com.soichi.hrm1017controller.util.ControllerNamesResolver;
import com.soichi.hrm1017controller.util.Debug;
import com.soichi.lib.ble.BleWrapper;
import com.soichi.lib.ble.BleWrapperUiCallbacks;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private InteractiveMonitorLayout mMonitor;

    private ClassicControllerLayout mClassicControllerLayout;

    private BleWrapper mBleWrapper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mMonitor = (InteractiveMonitorLayout) findViewById(R.id.interactive_monitor);
        mMonitor.setAdapter(mSectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mClassicControllerLayout == null) {
                    mClassicControllerLayout = (ClassicControllerLayout) findViewById(R.id.classic_controller_fragment);
                    mClassicControllerLayout.init();
                }
                mClassicControllerLayout.toggleVisibility();
            }
        });

        mBleWrapper = new BleWrapper(this, new BleWrapperUiCallbacks.Null() {

            @Override
            public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
                String msg = "uiDeviceFound: " + device.getName() + ", " + rssi + ", " + record.toString();
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                connect(device);
            }

            @Override
            public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device,
                                            List<BluetoothGattService> services) {

                for (BluetoothGattService service : services) {
                    String serviceName = ControllerNamesResolver.resolveServiceName(service.getUuid().toString());

                    if (Debug.ACTIVE) {
                        Log.d(Debug.LOGTAG, "service uuid is: " + service.getUuid() + " " + serviceName);
                    }
                }
            }

            @Override
            public void uiNewValueForCharacteristic(BluetoothGatt gatt, BluetoothDevice device,
                                                    BluetoothGattService service, BluetoothGattCharacteristic ch, String strValue, int intValue,
                                                    byte[] rawValue, String timestamp) {

                Log.d(Debug.LOGTAG, "ch value " + ch.getStringValue(0));

                // TODO needs a special fragment for this
                TextView textView = (TextView) findViewById(R.id.log_output);
                textView.setText(ch.getStringValue(0));
            }

            @Override
            public void uiGotNotification(BluetoothGatt gatt, BluetoothDevice device,
                                          BluetoothGattService service,
                                          BluetoothGattCharacteristic characteristic) {
                Log.d(Debug.LOGTAG, "uiGOtNotification called");
                Log.d("ble notification: ", characteristic.getStringValue(0));
            }

            @Override
            public void uiCharacteristicForService(BluetoothGatt gatt,
                                                   BluetoothDevice device, BluetoothGattService service,
                                                   List<BluetoothGattCharacteristic> chars) {
                super.uiCharacteristicForService(gatt, device, service, chars);
                Log.d(Debug.LOGTAG, "uuid " + service.getUuid());
                while (chars.iterator().hasNext()) {
                    //Log.d(Debug.LOGTAG, "uiCharacteristicForService " + chars.iterator().next());
                }
            }

            @Override
            public void uiCharacteristicsDetails(BluetoothGatt gatt,
                                                 BluetoothDevice device, BluetoothGattService service,
                                                 BluetoothGattCharacteristic characteristic) {
                super.uiCharacteristicsDetails(gatt, device, service, characteristic);
                Log.d(Debug.LOGTAG, "uiCharacteristicsDetails " + characteristic.toString());
            }
        });

        if (!Debug.isEmulator() && !mBleWrapper.checkBleHardwareAvailable()) {
            Toast.makeText(this, "No BLE-compatible hardware detected", Toast.LENGTH_SHORT).show();
            finish();
        }

        if (savedInstanceState == null) {
            final ClassicControllerFragment ctrl = new ClassicControllerFragment();
            ctrl.setBleWrapper(mBleWrapper);
            getFragmentManager().beginTransaction().add(R.id.root_container, ctrl).commit();
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
            p.setAnchorId(R.id.classic_controller_fragment);
            fab.setLayoutParams(p);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Debug.isEmulator()) {
            return;
        }

        if (!mBleWrapper.isBtEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBtIntent);
            finish();
        }
        Boolean result = mBleWrapper.initialize();
        if (!result) {
            Log.d(Debug.LOGTAG, "error occured in onResume");
            finish();
        }

        if (mBleWrapper.isConnected()) {
            return;
        }
        connect(mBleWrapper.getDevice());


    }

    @Override
    protected void onPause() {
        super.onPause();

        if (Debug.isEmulator()) {
            return;
        }

        mBleWrapper.diconnect();
        mBleWrapper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * connect to BLE peripheral
     *
     * @param device
     * @return boolean
     */
    private boolean connect(final BluetoothDevice device) {
        boolean status = false;

        if (device == null) {
            return status;
        }

        if (device.getName().equals("mbed AI robot")) {
            status = mBleWrapper.connect(device.getAddress());
            if (status == false) {
                if (Debug.ACTIVE) {
                    Log.d(Debug.LOGTAG, "uiDeviceFound: Connection problem");
                }
            }
        }
        return status;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        MainActivityOptionsItemAction action = MainActivityOptionsItemAction.valueOf(item);

        return action.getHandler().handle(this, mBleWrapper) || super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return new LogMonitorFragment();
                case 1:
                    return new MapController();
                case 2:
                    return new RadarMap();

            }

            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
