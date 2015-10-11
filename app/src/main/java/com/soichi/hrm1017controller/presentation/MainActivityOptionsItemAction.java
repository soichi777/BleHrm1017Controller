package com.soichi.hrm1017controller.presentation;

import android.view.MenuItem;

import com.soichi.hrm1017controller.R;
import com.soichi.hrm1017controller.presentation.menu.*;

public enum MainActivityOptionsItemAction {
    START_SCAN(R.id.action_start_scan, new StartScanActionHandler()),
    // TODO combine start and stop scan button
    STOP_SCAN(R.id.action_stop_scan, new StopScanActionHandler()),
    TOGGLE_CLASSIC_CONTROLLER(R.id.action_toggle_classic_controller, new ToggleClassicControllerActionHandler()),
    UNKNOWN(-1, new UnknownActionHandler());

    private final int mMenuId;
    private final OptionsItemActionHandler mHandler;

    MainActivityOptionsItemAction(final int menuId, final OptionsItemActionHandler handler) {
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
