package com.soichi.blehrm1017controller;

import com.soichi.blehrm1017controller.R;

import android.view.MenuItem;
import menu.HogeActionHandler;
import menu.OptionsItemActionHandler;
import menu.StartScanActionHandler;
import menu.StopScanActionHandler;
import menu.UnknownActionHandler;

public enum MainActivityOptionsItemAction {
	HOGE(R.id.action_settings, new HogeActionHandler()), 
	START_SCAN(R.id.action_start_scan, new StartScanActionHandler()), 
	STOP_SCAN(R.id.action_stop_scan, new StopScanActionHandler()), 
	UNKNOWN(-1,	new UnknownActionHandler());

	private final int mMenuId;
	private final OptionsItemActionHandler mHandler;

	private MainActivityOptionsItemAction(final int menuId, final OptionsItemActionHandler handler) {
		mMenuId = menuId;
		mHandler = handler;
	}

	public static MainActivityOptionsItemAction valueOf(MenuItem item) {
		for (MainActivityOptionsItemAction action : values()) {
			if (action.getMenuId() == item.getItemId()) {
				return action;
			}
		}
		return UNKNOWN;
	}

	public int getMenuId() {
		return mMenuId;
	}

	public OptionsItemActionHandler getHandler() {
		return mHandler;
	}
}
