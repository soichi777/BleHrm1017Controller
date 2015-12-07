package com.soichi.hrm1017controller.presentation.menuhandler.main;

import android.content.Context;
import android.app.Activity;

import com.soichi.hrm1017controller.presentation.menuhandler.OptionsItemActionHandler;
import com.soichi.lib.ble.BleWrapper;

public class ToggleClassicControllerActionHandler implements OptionsItemActionHandler {

    @Override
    public boolean handle(Context context, BleWrapper bleWrapper) {
        Activity activity = (Activity) context;
        //ClassicControllerFragment fragment = (ClassicControllerFragment) activity.getFragmentManager().findFragmentById(R.id.container);
        //fragment.getFragmentManager().popBackStack();
        //Log.d(Debug.LOGTAG, fragment.toString() + " is the fragment");
        activity.getFragmentManager().popBackStack();
        return true;
    }
}
