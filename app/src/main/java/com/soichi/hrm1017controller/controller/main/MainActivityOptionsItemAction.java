package com.soichi.hrm1017controller.controller.main;

import android.view.MenuItem;

import com.soichi.hrm1017controller.R;
import com.soichi.hrm1017controller.presentation.menuhandler.*;
import com.soichi.hrm1017controller.presentation.menuhandler.main.SettingsActionHandler;
import com.soichi.hrm1017controller.presentation.menuhandler.main.StartLogOutputActionHandler;
import com.soichi.hrm1017controller.presentation.menuhandler.main.StartScanActionHandler;
import com.soichi.hrm1017controller.presentation.menuhandler.main.StopScanActionHandler;
import com.soichi.hrm1017controller.presentation.menuhandler.main.ToggleClassicControllerActionHandler;

public enum MainActivityOptionsItemAction {
    START_SCAN(R.id.action_start_scan, new StartScanActionHandler()),
    // TODO combine start and stop scan button
    STOP_SCAN(R.id.action_stop_scan, new StopScanActionHandler()),
    TOGGLE_CLASSIC_CONTROLLER(R.id.action_toggle_classic_controller, new ToggleClassicControllerActionHandler()),
    START_LOG_OUTPUT(R.id.start_log_output, new StartLogOutputActionHandler()),
    SETTINGS_PAGE(R.id.action_settings, new SettingsActionHandler()),
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
