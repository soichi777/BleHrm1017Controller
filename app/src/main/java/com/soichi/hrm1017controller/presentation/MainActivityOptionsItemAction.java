package com.soichi.hrm1017controller.presentation;

import android.view.MenuItem;

import com.soichi.hrm1017controller.R;

import com.soichi.hrm1017controller.presentation.menu.HogeActionHandler;
import com.soichi.hrm1017controller.presentation.menu.OptionsItemActionHandler;
import com.soichi.hrm1017controller.presentation.menu.StartScanActionHandler;
import com.soichi.hrm1017controller.presentation.menu.StopScanActionHandler;
import com.soichi.hrm1017controller.presentation.menu.UnknownActionHandler;

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
