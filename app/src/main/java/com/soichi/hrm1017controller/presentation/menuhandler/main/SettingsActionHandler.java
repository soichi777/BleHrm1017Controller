package com.soichi.hrm1017controller.presentation.menuhandler.main;

import android.content.Context;
import android.content.Intent;

import com.soichi.hrm1017controller.controller.settings.SettingsActivity;
import com.soichi.hrm1017controller.presentation.menuhandler.OptionsItemActionHandler;
import com.soichi.lib.ble.BleWrapper;


public class SettingsActionHandler implements OptionsItemActionHandler {

    @Override
    public boolean handle(Context context, BleWrapper bleWrapper) {

        Intent i = new Intent(context, SettingsActivity.class);
        context.startActivity(i);
        return true;
    }
}
