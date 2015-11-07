package com.soichi.hrm1017controller.presentation;

import java.util.List;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.soichi.hrm1017controller.R;
import com.soichi.hrm1017controller.presentation.main.widget.ClassicControllerLayout;
import com.soichi.hrm1017controller.presentation.main.widget.InteractiveMonitorLayout;
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
					mClassicControllerLayout = (ClassicControllerLayout) findViewById(R.id.classic_controller);
					mClassicControllerLayout.init();

					//mMonitor.setControllerHeight(mClassicControllerLayout.getHeight());
					//mMonitor.setClassicController(mClassicControllerLayout);
				}
				mClassicControllerLayout.toggleVisibility();
			}
		});

		mBleWrapper = new BleWrapper(this, new BleWrapperUiCallbacks.Null() {

			@Override
			public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
				String msg = "uiDeviceFound: " + device.getName() + ", " + rssi + ", " + record.toString();
				// TODO "MainActivity.this" may be wrong???
				Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

				if (Debug.ACTIVE) {
					Log.d(Debug.LOGTAG, msg);
				}

				connect(device);
			}

			@Override
			public void uiAvailableServices(BluetoothGatt gatt, BluetoothDevice device,
					List<BluetoothGattService> services) {

				BluetoothGattCharacteristic c;

				for (BluetoothGattService service : services) {
					// String serviceName =
					// BleNamesResolver.resolveUuid(service.getUuid().toString());
					// TODO
					String serviceName = ControllerNamesResolver.resolveUuid(service.getUuid().toString());

					if (Debug.ACTIVE) {
						Log.d(Debug.LOGTAG, "service uuid is: " + service.getUuid() + " " + serviceName);
					}

					mBleWrapper.getCharacteristicsForService(service);
				}

				// mState = mSensorState.ACC_ENABLE;
			}

			@Override
			public void uiNewValueForCharacteristic(BluetoothGatt gatt, BluetoothDevice device,
					BluetoothGattService service, BluetoothGattCharacteristic ch, String strValue, int intValue,
					byte[] rawValue, String timestamp) {
				super.uiNewValueForCharacteristic(gatt, device, service, ch, strValue, intValue, rawValue, timestamp);

				if (Debug.ACTIVE) {
					Log.d(Debug.LOGTAG, "uiNewValueForCharacteristic");
				}

				for (byte b : rawValue) {
					if (Debug.ACTIVE) {
						Log.d(Debug.LOGTAG, "Val: " + b);
					}
				}

			}
		});

		if (!Debug.isEmulator() && !mBleWrapper.checkBleHardwareAvailable()) {
			Toast.makeText(this, "No BLE-compatible hardware detected", Toast.LENGTH_SHORT).show();
			finish();
		}

		if (savedInstanceState == null) {
			final ClassicControllerFragment ctrl = new ClassicControllerFragment();
			ctrl.setBleWrapper(mBleWrapper);
			//getFragmentManager().beginTransaction().add(R.id.container, ctrl).commit();
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
			return PlaceholderFragment.newInstance(position + 1);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.section_label);
			textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
		}
	}

}
