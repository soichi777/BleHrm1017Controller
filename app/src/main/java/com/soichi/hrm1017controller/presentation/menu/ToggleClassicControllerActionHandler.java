package com.soichi.hrm1017controller.presentation.menu;

import android.content.Context;
import android.app.Activity;
import android.util.Log;

import com.soichi.hrm1017controller.R;
import com.soichi.hrm1017controller.presentation.ClassicControllerFragment;
import com.soichi.hrm1017controller.util.Debug;
import com.soichi.lib.ble.BleWrapper;

public class ToggleClassicControllerActionHandler implements OptionsItemActionHandler {

    @Override
    public boolean handle(Context context, BleWrapper bleWrapper) {
        Activity activity = (Activity) context;
        ClassicControllerFragment fragment = (ClassicControllerFragment) activity.getFragmentManager().findFragmentById(R.id.classic_controller_fragment);
        fragment.getFragmentManager().popBackStack();
        Log.d(Debug.LOGTAG, activity.toString() + " is the activity");
        return true;
    }
}
